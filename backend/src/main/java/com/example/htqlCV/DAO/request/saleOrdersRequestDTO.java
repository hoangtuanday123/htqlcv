package com.example.htqlCV.DAO.request;

import java.util.List;
import java.util.UUID;

import lombok.Data;
@Data
public class saleOrdersRequestDTO {
    private Long totalAmount;
    private Long totalAmountPaid;
    private UUID customerId;
    private String status;
    private String SubStatus;
    private List<saleOrderItemsRequestDTO>saleOrderItemsRequestDTO;
    private UUID businessId;
}
