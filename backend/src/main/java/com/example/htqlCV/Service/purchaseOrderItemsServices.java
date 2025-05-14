package com.example.htqlCV.Service;

import java.util.List;

import com.example.htqlCV.DAO.request.purchaseOrderItemsRequestDTO;
import com.example.htqlCV.Model.purchaseOrderItems;

public interface purchaseOrderItemsServices {
    List<purchaseOrderItems> getPurchaseOrderItemsByPurchaseOrder(Long purchaseOrdersId);
    purchaseOrderItems getPurchaseOrderItemsById(Long id);
    Long createPurchaseOrderItems(purchaseOrderItemsRequestDTO purchaseOrderItems);
    void updatePurchaseOrderItems(Long id, purchaseOrderItemsRequestDTO purchaseOrderItems);
    void deletePurchaseOrderItems(Long id);
}
