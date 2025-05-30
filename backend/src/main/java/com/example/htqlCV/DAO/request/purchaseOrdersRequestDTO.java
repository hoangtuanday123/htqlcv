package com.example.htqlCV.DAO.request;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class purchaseOrdersRequestDTO {
    private Long totalAmount;
    private Long totalAmountPaid;
    private UUID supplierId;
    private String status;
    private String SubStatus;
    private List<purchaseOrderItemsRequestDTO>purchaseOrderItemsRequestDTO;
    private UUID businessId;
}
