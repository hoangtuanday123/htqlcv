package com.example.htqlCV.DAO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class dashboardResponse {
    private int totalCustomer;
    private int totalStaff;
    private int totalSupplier;
    private Long totalAmoundThisMonth;
    private Long totalAmoundPurchaseThisMonth;
    private Long totalProfit;
    private int stockQuantity;
}
