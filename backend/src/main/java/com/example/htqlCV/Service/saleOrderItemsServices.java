package com.example.htqlCV.Service;

import java.util.List;

import com.example.htqlCV.DAO.request.saleOrderItemsRequestDTO;
import com.example.htqlCV.Model.saleOrderItems;

public interface saleOrderItemsServices {
    List<saleOrderItems> getSaleOrderItemsBySaleOrder(Long saleOrdersId);
    saleOrderItems getSaleOrderItemsById(Long id);
    saleOrderItems getSaleOrderItemsByProductIDSaleOrder(Long saleOrdersId, Long productId);
    Long createSaleOrderItems(saleOrderItemsRequestDTO saleOrderItems);
    void updateSaleOrderItems(Long id, saleOrderItemsRequestDTO saleOrderItems);
    void deleteSaleOrderItems(Long id);
}
