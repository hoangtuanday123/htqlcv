package com.example.htqlCV.DAO.request;

import java.util.List;

import lombok.Data;

@Data
public class saleOrdersRequestDTO {
    private Long totalAmount;
    private Long totalAmountPaid;
    private Long customerId;
    private String status;
    private String SubStatus;
    private List<saleOrderItemsRequestDTO>saleOrderItemsRequestDTO;
}
