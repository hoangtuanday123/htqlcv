package com.example.htqlCV.DAO.request;

import java.util.UUID;

import lombok.Data;
@Data
public class productRequestDTO {
    private String name;
    private Long capitalPrice;
    private Long salePrice;
    private Long stockQuantity;
    private UUID categoryId;
    private UUID branchProductId;
    private UUID businessId;
}
