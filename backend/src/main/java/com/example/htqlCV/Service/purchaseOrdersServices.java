package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.purchaseOrdersRequestDTO;
import com.example.htqlCV.Model.purchaseOrders;
public interface  purchaseOrdersServices {
    List<purchaseOrders> getAllPurchaseOrders(UUID businessId);
    purchaseOrders getPurchaseOrdersById(UUID id);
    UUID createPurchaseOrders(purchaseOrdersRequestDTO purchaseOrders);
    void updatePurchaseOrders(UUID id, purchaseOrdersRequestDTO purchaseOrders);
    void deletePurchaseOrders(UUID id);
}
