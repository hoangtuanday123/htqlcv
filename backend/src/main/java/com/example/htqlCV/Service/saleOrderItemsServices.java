package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.saleOrderItemsRequestDTO;
import com.example.htqlCV.Model.saleOrderItems;
public interface saleOrderItemsServices {
    List<saleOrderItems> getSaleOrderItemsBySaleOrder(UUID saleOrdersId);
    saleOrderItems getSaleOrderItemsById(UUID id);
    saleOrderItems getSaleOrderItemsByProductIDSaleOrder(UUID saleOrdersId, UUID productId);
    UUID createSaleOrderItems(saleOrderItemsRequestDTO saleOrderItems);
    void updateSaleOrderItems(UUID id, saleOrderItemsRequestDTO saleOrderItems);
    void deleteSaleOrderItems(UUID id);
}
