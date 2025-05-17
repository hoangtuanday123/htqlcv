package com.example.htqlCV.DAO.request;

import lombok.Data;

@Data
public class saleOrderItemsRequestDTO {
    private Long id;
    private Long saleOrdersId;
    private Long productId;
    private Integer quantity;
    private Long unitPrice;
    private String note;
}
