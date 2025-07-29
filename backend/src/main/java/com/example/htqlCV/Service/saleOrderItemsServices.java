package com.example.htqlCV.Service;

import java.util.List;


import com.example.htqlCV.DAO.request.saleOrderItemsRequestDTO;
import com.example.htqlCV.Model.saleOrderItems;
public interface saleOrderItemsServices {
    List<saleOrderItems> getSaleOrderItemsBySaleOrder(String saleOrdersId);
    saleOrderItems getSaleOrderItemsById(String id);
    saleOrderItems getSaleOrderItemsByProductIDSaleOrder(String saleOrdersId, String productId);
    String createSaleOrderItems(saleOrderItemsRequestDTO saleOrderItems);
    void updateSaleOrderItems(String id, saleOrderItemsRequestDTO saleOrderItems);
    void deleteSaleOrderItems(String id);
}
