package com.example.htqlCV.Service;

import java.util.List;


import com.example.htqlCV.DAO.request.saleOrdersRequestDTO;
import com.example.htqlCV.DAO.response.RevenueByProductAndMonth;
import com.example.htqlCV.Model.saleOrders;
public interface saleOrdersServices {
    List<saleOrders> getAllSaleOrders(String businessId);
    saleOrders getSaleOrdersById(String id);
    String createSaleOrder(saleOrdersRequestDTO saleOrdersRequestDTO);
    void updateSaleOrder(String id,saleOrdersRequestDTO saleOrdersRequestDTO);
    void deleteSaleOrder(String id);
    Long getTotalAmountThisMonthByBusinessId(String businessId);
    Long getMonthlyProfitByBusinessId(String businessId);
    void refundSaleOrder(String id,saleOrdersRequestDTO saleOrdersRequestDTO);
    List<RevenueByProductAndMonth> getMonthlyRevenueNative(Integer currentYear,String businessId);
   Long getTotalAmountThisDailyByBusinessId(String businessId);
   Long getDailyProfitByBusinessId(String businessId);
}