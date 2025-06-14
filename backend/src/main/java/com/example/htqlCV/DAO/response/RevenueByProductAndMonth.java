package com.example.htqlCV.DAO.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RevenueByProductAndMonth {
    private String productName;
    private Integer month;
    private BigDecimal   totalRevenue;
}
