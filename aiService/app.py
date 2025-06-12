import os
from langchain.chat_models import init_chat_model
from langchain_core.messages import HumanMessage
import json
import re
from configs import configs
from fastapi.openapi.docs import get_swagger_ui_html
from fastapi import FastAPI, HTTPException, status
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
    mydb = mysql.connector.connect(
        host=configs.MYSQL_HOST,
        user=configs.MYSQL_USER,
        password=configs.MYSQL_PASSWORD,
        database=configs.MYSQL_DB
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
                uuid_bytes = uuid.UUID(business_id).bytes
            except ValueError:
                raise ValueError("business_id không hợp lệ (không đúng định dạng UUID)")

            # Ép buộc lọc theo business_id
            where_clauses.append("`business_id` = %s")
            params.append(uuid_bytes)

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
    async def execute(business_id: str,text:str):
        # print("🔍 Nhận câu truy vấn:", text)
        cursor = mydb.cursor()
        os.environ["LANGSMITH_TRACING"] = "true"
        os.environ["LANGSMITH_API_KEY"] = "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"
        os.environ["LANGSMITH_PROJECT"] = "default"
        os.environ["GOOGLE_API_KEY"] = "AIzaSyCouB8i4EIecncz8fPKGYtqYqlpa-FgiKo"

        # Khởi tạo mô hình Gemini từ Google
        model = init_chat_model("gemini-2.0-flash", model_provider="google_genai")

        # Câu truy vấn tiếng Việt cần phân tích
        query = text

        # Gửi câu truy vấn vào mô hình để phân tích
        prompt = f"""
        Dựa trên câu sau:
        "{query}"

        Hãy phân tích và trả về kết quả dưới dạng JSON theo định dạng sau:
        {{
        "table": "customer",
        "action": "select",
        "condition": thông tin cần tìm kiếm dưới dạng json,
        }}
        Chỉ trả về đúng JSON.
        key là "table", "action", "condition","created_at" để trả lời cho câu hỏi ngày tháng năm.
        key của bảng customer là "name","phone","dob","address","companyName","customerType","cmnd","email","mst".
        key của bảng product là "name","capital_price","sale_price","branchProduct","category".
        key của bảng supplier là "name","phone","address","email","company","mst","branch".
        key của bảng purchase_orders là "totalAmount","totalAmountPaid","status" là [Processing, Canceled, Completed, None],"sub_status" là[ None,Not Paid ].
        key của bảng sale_orders là "totalAmount","totalAmountPaid","status" là [Processing, Canceled, Completed, None],"sub_status" là [ None,Not Paid ].
        """
        
        response = model.invoke(prompt)
        raw_output = response.content.strip()
        if raw_output.startswith("```json"):
            raw_output = re.sub(r"^```json\s*|\s*```$", "", raw_output.strip())

        # Parse JSON
        try:
            data = json.loads(raw_output)
            
            query, params = build_select_query_param(data,business_id)
            print("🔍 Câu truy vấn SQL:", query)
            print("📋 Tham số:", params)
            cursor.execute(query, params)
            results = cursor.fetchall()
            print("🔍 Kết quả truy vấn:", results)
            columns = [desc[0] for desc in cursor.description]
            # Trả về JSON an toàn
            output = []
            for row in results:
                row_dict = {col: safe_decode(val) for col, val in zip(columns, row)}
                output.append(row_dict)
            response = model.invoke(f"""Dựa trên kết quả {output}, hãy trả lời câu hỏi của người dùng không cần TRẢ LỜI 'id','created_at','updated_at' và 'deleted_at','is_blocked',các từ dính chữ id,QR, những từ có tính chất như mã.
                                    nếu {output} là rỗng thì trả lời 'Không tìm thấy kết quả nào' và không cần đưa ra câu hỏi của người dùng.""")
            return {"message":response.content.strip()}
        except json.JSONDecodeError as e:
            print("❌ Lỗi khi parse JSON:", e)
            print("🔎 Raw response:\n", raw_output)
    return app

app= start_app()

