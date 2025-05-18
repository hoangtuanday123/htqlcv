package com.example.htqlCV.DAO.request;

import java.util.UUID;

import lombok.Data;
@Data
public class saleOrderItemsRequestDTO {
    private UUID id;
    private UUID saleOrdersId;
    private UUID productId;
    private Integer quantity;
    private Long unitPrice;
    private String note;
}
