package com.example.htqlCV.DAO.request;

import java.util.UUID;

import lombok.Data;
@Data
public class purchaseOrderItemsRequestDTO {
    private UUID id;
    private UUID purchaseOrdersId;
    private UUID productId;
    private Integer quantity;
    private Long unitPrice;
    private String note;
}
