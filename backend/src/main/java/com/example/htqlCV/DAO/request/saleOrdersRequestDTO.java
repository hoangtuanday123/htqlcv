package com.example.htqlCV.DAO.request;

import java.util.List;


import lombok.Data;
@Data
public class saleOrdersRequestDTO {
    private Long totalAmount;
    private Long totalAmountPaid;
    private String customerId;
    private String status;
    private String SubStatus;
    private List<saleOrderItemsRequestDTO>saleOrderItemsRequestDTO;
    private String businessId;
    private String qrcodeId;
}
