package com.example.htqlCV.Service;

import java.util.List;


import com.example.htqlCV.DAO.request.purchaseOrderItemsRequestDTO;
import com.example.htqlCV.Model.purchaseOrderItems;
public interface purchaseOrderItemsServices {
    List<purchaseOrderItems> getPurchaseOrderItemsByPurchaseOrder(String purchaseOrdersId);
    purchaseOrderItems getPurchaseOrderItemsById(String id);
    purchaseOrderItems getPurchaseOrderItemsByProductIDPurchaseOrder(String purchaseOrdersId, String productId);
    String createPurchaseOrderItems(purchaseOrderItemsRequestDTO purchaseOrderItems);
    void updatePurchaseOrderItems(String id, purchaseOrderItemsRequestDTO purchaseOrderItems);
    void deletePurchaseOrderItems(String id);
}
