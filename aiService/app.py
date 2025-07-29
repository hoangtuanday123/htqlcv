import os
from langchain.chat_models import init_chat_model
from langchain_core.messages import HumanMessage
import json
import re
from configs import configs
from fastapi.openapi.docs import get_swagger_ui_html
from fastapi import FastAPI, HTTPException, status,Depends
from pydantic import BaseModel
import mysql.connector
from fastapi.staticfiles import StaticFiles
import hashlib
import uuid
import base64
def start_app():
    app = FastAPI(
        title=configs.PROJECT_NAME + ' - ' + configs.ENV, 
        version=configs.PROJECT_VERSION, 
        debug=configs.DEBUG_MODE, 
        docs_url=None,
        openapi_url="/openapi.json",
        separate_input_output_schemas=False
    )
    app.mount("/static", StaticFiles(directory="static"), name="static")
    @app.get("/docs", include_in_schema=False)
    async def custom_swagger_ui_html():
        return get_swagger_ui_html(
            openapi_url=app.openapi_url,
            title=app.title + " - Swagger UI", 
            oauth2_redirect_url=app.swagger_ui_oauth2_redirect_url,
            swagger_js_url="/static/swagger/swagger-ui-bundle.js",
            swagger_css_url="/static/swagger/swagger-ui.css",
        )
    
    
    # Connect to the database
    
    def get_db():
        db = mysql.connector.connect(
            host=configs.MYSQL_HOST,
            user=configs.MYSQL_USER,
            password=configs.MYSQL_PASSWORD,
            database=configs.MYSQL_DB
        )
        try:
            yield db
        finally:
            db.close()

    
    def to_mysql_binary(uuid_str: str) -> bytes:
        u = uuid.UUID(uuid_str)
        b = u.bytes
        return (
            b[3::-1] +    # đảo 4 byte đầu
            b[5:3:-1] +   # đảo 2 byte tiếp
            b[7:5:-1] +   # đảo 2 byte tiếp
            b[8:]         # giữ nguyên 8 byte còn lại
        )


    def build_select_query_param(data: dict, business_id: str) -> tuple[str, list]:
        try:
            if data.get("action", "").lower() != "select":
                raise ValueError("Chỉ hỗ trợ hành động 'select'")

            table = "tbl_" + data["table"]
            conditions = data.get("condition", {})

            where_clauses = []
            params = []
            try:
                
                print(f"🔍 business_id  {business_id}")
                
            except ValueError:
                raise ValueError("business_id không hợp lệ (không đúng định dạng UUID)")

            # Ép buộc lọc theo business_id
            where_clauses.append("`business_id` = %s")
            params.append(business_id)

            for key, value in conditions.items():
                if key in ["status", "sub_status"] and isinstance(value, list):
                    placeholders = ', '.join(['%s'] * len(value))
                    where_clauses.append(f"`{key}` IN ({placeholders})")
                    params.extend(value)
                else:
                    where_clauses.append(f"`{key}` = %s")
                    params.append(value)

            query = f"SELECT * FROM `{table}`"
            if where_clauses:
                query += " WHERE " + " AND ".join(where_clauses)

            return query, params
        except Exception as e:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail=f"Lỗi khi xây dựng câu truy vấn: {str(e)}"
            )


    def safe_decode(value):
        if isinstance(value, bytes):
            try:
                return value.decode("utf-8")
            except UnicodeDecodeError:
                return base64.b64encode(value).decode("utf-8")
        return value

    @app.get("/")
    async def execute(business_id: str,text:str,mydb=Depends(get_db)):
        # print("🔍 Nhận câu truy vấn:", text)
        cursor = mydb.cursor()
        os.environ["LANGSMITH_TRACING"] = configs.LANGSMITH_TRACING
        os.environ["LANGSMITH_API_KEY"] = configs.LANGSMITH_API_KEY
        os.environ["LANGSMITH_PROJECT"] = configs.LANGSMITH_PROJECT
        os.environ["GOOGLE_API_KEY"] = configs.GOOGLE_API_KEY
        # Mô tả schema (giúp GPT hiểu cấu trúc database)
        schema = """
        Mysql tables:
        tbl_customer(name, phone, dob, address , companyName, customerType, cmnd, email , mst)
        tbl_product(name, capital_price, sale_price , branch_product_id->tbl_product , category_id->tbl_product)
        tbl_supplier(name, phone, address , email , company , mst , branch)
        tbl_purchase_orders(total_amount, total_amount_paid, status : [Processing, Canceled, Completed, None], sub_status : [ None,Not Paid ])
        tbl_sale_orders(total_amount, total_amount_paid, status : [Processing, Canceled, Completed, None], sub_status : [ None,Not Paid ])
        tbl_guarantee(name, guaranteeTime, product_id -> tbl_product)
        tbl_branch_product(name)
        tbl_category(name)
        tbl_purchase_order_items(purchase_orders_id -> tbl_purchase_orders, product_id -> tbl_product, quantity, unit_price,supplier_id->tbl_supplier)
        tbl_sale_order_items(sale_orders_id -> tbl_sale_orders, product_id -> tbl_product, quantity, unit_price,customer_id->tbl_customer)
        """


        # Prompt gửi vào GPT
        prompt = f"""
        ⚠️ Quy tắc bắt buộc:
        - Mọi câu SQL phải có điều kiện WHERE business_id = %s (với tên bảng chính xác).
        - Nếu truy vấn nhiều bảng, mỗi bảng liên quan phải lọc theo business_id = %s.
        - Truy vấn phải dùng parameterized query (không gán trực tiếp giá trị).
        - Không bao giờ bỏ qua business_id, kể cả khi không được nhắc trong câu hỏi.
        ### Database Schema:
        {schema}

        ### Question:
        {text}

        ### SQL Query (parameterized, with WHERE business_id = ?):
        SELECT
        ### Ví dụ 1:
        # Yêu cầu: Lấy tất cả khách hàng
        # Trả về:
        SELECT * FROM tbl_customer WHERE business_id = ?;
        """

        # Khởi tạo mô hình Gemini từ Google
        model = init_chat_model("gemini-2.0-flash", model_provider="google_genai")

        response = model.invoke(prompt)
        raw_output = response.content.strip()
        print("🔍 Kết quả trả về:", raw_output)
        if raw_output.startswith("```"):
            raw_output = re.sub(r"^```sql\s*|\s*```$", "", raw_output.strip())
        if "business_id" not in raw_output.lower():
            raise ValueError("❌ SQL không chứa điều kiện lọc business_id. Truy vấn bị từ chối.")
        raw_output = raw_output.replace("?", "%s")
        forbidden_statements = ["delete", "update", "insert", "drop", "alter", "truncate"]
        if any(stmt in raw_output.lower() for stmt in forbidden_statements):
            raise ValueError("❌ Truy vấn bị từ chối: chỉ cho phép SELECT có điều kiện WHERE business_id.")
        print("🔍 Kết quả trả về:", raw_output)
        
        try:
            cursor.execute(raw_output,  (business_id,))
            results = cursor.fetchall()
            print("🔍 Kết quả truy vấn:", results)
            columns = [desc[0] for desc in cursor.description]
            output = []
            for row in results:
                row_dict = {col: safe_decode(val) for col, val in zip(columns, row)}
                output.append(row_dict)
            promt_response=f"""
            Dựa vào JSON dưới đây, hãy trả lời câu hỏi của người dùng. Tuy nhiên, chỉ hiển thị các thông tin quan trọng cho người dùng,id , và bỏ qua các trường có tên là *_id, qrcode_url hoặc qr. Trình bày kết quả rõ ràng, thân thiện
            JSON:
            {output}
            """            
            response = model.invoke(promt_response)
            return {"message": response.content.strip()}
        except mysql.connector.Error as e:
            print("❌ Lỗi khi thực thi câu truy vấn:", e)
        
    return app

app= start_app()

