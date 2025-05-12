package com.example.htqlCV.DAO.request;

import lombok.Data;

@Data
public class productRequestDTO {
    private String name;
    private Long capitalPrice;
    private Long salePrice;
    private Long stockQuantity;
    private Long categoryId;
    private Long branchProductId;
}
