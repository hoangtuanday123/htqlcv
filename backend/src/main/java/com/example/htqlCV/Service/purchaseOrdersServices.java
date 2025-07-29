package com.example.htqlCV.Service;

import java.util.List;


import com.example.htqlCV.DAO.request.purchaseOrdersRequestDTO;
import com.example.htqlCV.Model.purchaseOrders;
public interface  purchaseOrdersServices {
    List<purchaseOrders> getAllPurchaseOrders(String businessId);
    purchaseOrders getPurchaseOrdersById(String id);
    String createPurchaseOrders(purchaseOrdersRequestDTO purchaseOrders);
    void updatePurchaseOrders(String id, purchaseOrdersRequestDTO purchaseOrders);
    void deletePurchaseOrders(String id);
    Long getTotalAmountThisMonthByBusinessId(String businessId);
    void refundPurchaseOrder(String id,purchaseOrdersRequestDTO purchaseOrdersRequestDTO);
}
