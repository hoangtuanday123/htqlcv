package com.example.htqlCV.DAO.request;



import lombok.Data;
@Data
public class productRequestDTO {
    private String sku;
    private String name;
    private Long capitalPrice;
    private Long salePrice;
    private Long stockQuantity;
    private String unitCalculate;
    private String categoryId;
    private String branchProductId;
    private String businessId;
    private String qrcodeUrl;
}
