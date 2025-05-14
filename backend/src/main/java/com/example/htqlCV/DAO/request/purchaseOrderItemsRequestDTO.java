package com.example.htqlCV.DAO.request;

import lombok.Data;

@Data
public class purchaseOrderItemsRequestDTO {
    private Long id;
    private Long purchaseOrdersId;
    private Long productId;
    private Integer quantity;
    private Long unitPrice;
}
