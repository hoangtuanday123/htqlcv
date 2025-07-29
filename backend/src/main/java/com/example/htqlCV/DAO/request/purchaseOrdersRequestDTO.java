package com.example.htqlCV.DAO.request;

import java.util.List;


import lombok.Data;

@Data
public class purchaseOrdersRequestDTO {
    private Long totalAmount;
    private Long totalAmountPaid;
    private String supplierId;
    private String status;
    private String SubStatus;
    private List<purchaseOrderItemsRequestDTO>purchaseOrderItemsRequestDTO;
    private String businessId;
    private String qrcodeId;
}
