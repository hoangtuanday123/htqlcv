package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.saleOrdersRequestDTO;
import com.example.htqlCV.DAO.response.RevenueByProductAndMonth;
import com.example.htqlCV.Model.saleOrders;
public interface saleOrdersServices {
    List<saleOrders> getAllSaleOrders(UUID businessId);
    saleOrders getSaleOrdersById(UUID id);
    UUID createSaleOrder(saleOrdersRequestDTO saleOrdersRequestDTO);
    void updateSaleOrder(UUID id,saleOrdersRequestDTO saleOrdersRequestDTO);
    void deleteSaleOrder(UUID id);
    Long getTotalAmountThisMonthByBusinessId(UUID businessId);
    Long getMonthlyProfitByBusinessId(UUID businessId);
    void refundSaleOrder(UUID id,saleOrdersRequestDTO saleOrdersRequestDTO);
    List<RevenueByProductAndMonth> getMonthlyRevenueNative(Integer currentYear,UUID businessId);
   Long getTotalAmountThisDailyByBusinessId(UUID businessId);
   Long getDailyProfitByBusinessId(UUID businessId);
}