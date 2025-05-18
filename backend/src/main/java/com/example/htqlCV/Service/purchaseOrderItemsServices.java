package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.purchaseOrderItemsRequestDTO;
import com.example.htqlCV.Model.purchaseOrderItems;
public interface purchaseOrderItemsServices {
    List<purchaseOrderItems> getPurchaseOrderItemsByPurchaseOrder(UUID purchaseOrdersId);
    purchaseOrderItems getPurchaseOrderItemsById(UUID id);
    purchaseOrderItems getPurchaseOrderItemsByProductIDPurchaseOrder(UUID purchaseOrdersId, UUID productId);
    UUID createPurchaseOrderItems(purchaseOrderItemsRequestDTO purchaseOrderItems);
    void updatePurchaseOrderItems(UUID id, purchaseOrderItemsRequestDTO purchaseOrderItems);
    void deletePurchaseOrderItems(UUID id);
}
