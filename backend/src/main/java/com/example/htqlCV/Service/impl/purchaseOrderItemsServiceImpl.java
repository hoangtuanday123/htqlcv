package com.example.htqlCV.Service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.purchaseOrderItemsRequestDTO;
import com.example.htqlCV.Model.purchaseOrderItems;
import com.example.htqlCV.Respository.productRespository;
import com.example.htqlCV.Respository.purchaseOrderItemRespository;
import com.example.htqlCV.Respository.purchaseOrdersRespository;
import com.example.htqlCV.Service.purchaseOrderItemsServices;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class purchaseOrderItemsServiceImpl implements purchaseOrderItemsServices{
    private final purchaseOrderItemRespository purchaseOrderItemRespository;
    private final productRespository productRespository;
    private final purchaseOrdersRespository purchaseOrdersRespository;
    @Override
    public List<purchaseOrderItems> getPurchaseOrderItemsByPurchaseOrder(String purchaseOrdersId) {
        return purchaseOrderItemRespository.findByPurchaseOrders_Id(purchaseOrdersId);
    }
    @Override
    public purchaseOrderItems getPurchaseOrderItemsById(String id) {
        return purchaseOrderItemRespository.findById(id).orElse(null);
    }
    @Override
    public purchaseOrderItems getPurchaseOrderItemsByProductIDPurchaseOrder(String purchaseOrdersId, String productId){
        return purchaseOrderItemRespository.findByPurchaseOrders_IdAndProduct_Id(purchaseOrdersId, productId);
    }

    @Override
    public String createPurchaseOrderItems(purchaseOrderItemsRequestDTO purchaseOrderItemsRequestDTO) {
        var product = productRespository.findById(purchaseOrderItemsRequestDTO.getProductId()).orElse(null);
        var purchaseOrders = purchaseOrdersRespository.findById(purchaseOrderItemsRequestDTO.getPurchaseOrdersId()).orElse(null);
        purchaseOrderItems purchaseOrderItems_value = purchaseOrderItems.builder()
                .purchaseOrders(purchaseOrders)
                .product(product)
                .quantity(purchaseOrderItemsRequestDTO.getQuantity())
                .unitPrice(purchaseOrderItemsRequestDTO.getUnitPrice())
                .note(purchaseOrderItemsRequestDTO.getNote())
                .build();
        purchaseOrderItemRespository.save(purchaseOrderItems_value);
        return purchaseOrderItems_value.getId();
    }
    @Override
    public void updatePurchaseOrderItems(String id, purchaseOrderItemsRequestDTO purchaseOrderItemsRequestDTO) {
        purchaseOrderItems purchaseOrderItems_value = purchaseOrderItemRespository.findById(id).orElse(null);
        if (purchaseOrderItems_value != null) {
            purchaseOrderItems_value.setQuantity(purchaseOrderItemsRequestDTO.getQuantity());
            purchaseOrderItems_value.setUnitPrice(purchaseOrderItemsRequestDTO.getUnitPrice());
            purchaseOrderItems_value.setNote(purchaseOrderItemsRequestDTO.getNote());
            purchaseOrderItemRespository.save(purchaseOrderItems_value);
        }
    }

    @Override
    public void deletePurchaseOrderItems(String id) {
        purchaseOrderItems item = purchaseOrderItemRespository.findById(id)
        .orElseThrow(() -> new RuntimeException("Not found"));

        // Ngắt liên kết để tránh lỗi ràng buộc
        item.setProduct(null);
        item.setPurchaseOrders(null);

        purchaseOrderItemRespository.save(item); // cập nhật trước khi xóa
        purchaseOrderItemRespository.delete(item);
        // purchaseOrderItemRespository.deleteById(id);
    }
}
