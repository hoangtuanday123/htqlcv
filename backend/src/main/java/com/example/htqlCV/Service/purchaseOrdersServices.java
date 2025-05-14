package com.example.htqlCV.Service;

import java.util.List;

import com.example.htqlCV.DAO.request.purchaseOrdersRequestDTO;
import com.example.htqlCV.Model.purchaseOrders;

public interface  purchaseOrdersServices {
    List<purchaseOrders> getAllPurchaseOrders();
    purchaseOrders getPurchaseOrdersById(Long id);
    Long createPurchaseOrders(purchaseOrdersRequestDTO purchaseOrders);
    void updatePurchaseOrders(Long id, purchaseOrdersRequestDTO purchaseOrders);
    void deletePurchaseOrders(Long id);
}
