package com.example.htqlCV.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.purchaseOrderItemsRequestDTO;
import com.example.htqlCV.Model.purchaseOrderItems;
import com.example.htqlCV.Respository.purchaseOrderItemRespository;
import com.example.htqlCV.Respository.purchaseOrdersRespository;
import com.example.htqlCV.Service.purchaseOrderItemsServices;
import com.example.htqlCV.Respository.productRespository;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class purchaseOrderItemsServiceImpl implements purchaseOrderItemsServices{
    private final purchaseOrderItemRespository purchaseOrderItemRespository;
    private final productRespository productRespository;
    private final purchaseOrdersRespository purchaseOrdersRespository;
    @Override
    public List<purchaseOrderItems> getPurchaseOrderItemsByPurchaseOrder(Long purchaseOrdersId) {
        return purchaseOrderItemRespository.findByPurchaseOrders_Id(purchaseOrdersId);
    }
    @Override
    public purchaseOrderItems getPurchaseOrderItemsById(Long id) {
        return purchaseOrderItemRespository.findById(id).orElse(null);
    }

    @Override
    public Long createPurchaseOrderItems(purchaseOrderItemsRequestDTO purchaseOrderItemsRequestDTO) {
        var product = productRespository.findById(purchaseOrderItemsRequestDTO.getProductId()).orElse(null);
        var purchaseOrders = purchaseOrdersRespository.findById(purchaseOrderItemsRequestDTO.getPurchaseOrdersId()).orElse(null);
        purchaseOrderItems purchaseOrderItems_value = purchaseOrderItems.builder()
                .purchaseOrders(purchaseOrders)
                .quantity(purchaseOrderItemsRequestDTO.getQuantity())
                .unitPrice(purchaseOrderItemsRequestDTO.getUnitPrice())
                .build();
        purchaseOrderItemRespository.save(purchaseOrderItems_value);
        return purchaseOrderItems_value.getId();
    }
    @Override
    public void updatePurchaseOrderItems(Long id, purchaseOrderItemsRequestDTO purchaseOrderItemsRequestDTO) {
        purchaseOrderItems purchaseOrderItems_value = purchaseOrderItemRespository.findById(id).orElse(null);
        if (purchaseOrderItems_value != null) {
            purchaseOrderItems_value.setQuantity(purchaseOrderItemsRequestDTO.getQuantity());
            purchaseOrderItems_value.setUnitPrice(purchaseOrderItemsRequestDTO.getUnitPrice());
            purchaseOrderItemRespository.save(purchaseOrderItems_value);
        }
    }

    @Override
    public void deletePurchaseOrderItems(Long id) {
        purchaseOrderItemRespository.deleteById(id);
    }
}
