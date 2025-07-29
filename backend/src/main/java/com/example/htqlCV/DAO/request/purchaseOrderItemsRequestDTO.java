package com.example.htqlCV.DAO.request;



import lombok.Data;
@Data
public class purchaseOrderItemsRequestDTO {
    private String id;
    private String purchaseOrdersId;
    private String productId;
    private Integer quantity;
    private Long unitPrice;
    private String note;
}
