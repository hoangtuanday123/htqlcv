package com.example.htqlCV.DAO.request;



import lombok.Data;
@Data
public class saleOrderItemsRequestDTO {
    private String id;
    private String saleOrdersId;
    private String productId;
    private Integer quantity;
    private Long unitPrice;
    private String note;
}
