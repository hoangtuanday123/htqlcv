package com.example.htqlCV.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.purchaseOrdersRequestDTO;
import com.example.htqlCV.Model.purchaseOrders;
import com.example.htqlCV.Respository.purchaseOrdersRespository;
import com.example.htqlCV.Respository.supplierRespository;
import com.example.htqlCV.Service.purchaseOrderItemsServices;
import com.example.htqlCV.Service.purchaseOrdersServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class purchaseOrdersServiceImpl implements purchaseOrdersServices {
   private final purchaseOrdersRespository purchaseOrdersRespository;
   private final supplierRespository supplierRespository;
   private final purchaseOrderItemsServices purchaseOrderItemsServices;
    @Override
    public List<purchaseOrders> getAllPurchaseOrders() {
        return purchaseOrdersRespository.findAll();
    }
    @Override
    public purchaseOrders getPurchaseOrdersById(Long id) {
        return purchaseOrdersRespository.findById(id).orElse(null);
    }
    @Override
    public Long createPurchaseOrders(purchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        var supplier = supplierRespository.findById(purchaseOrdersRequestDTO.getSupplierId()).orElse(null);
        purchaseOrders purchaseOrders_value = purchaseOrders.builder()
                .totalAmount(purchaseOrdersRequestDTO.getTotalAmount())
                .totalAmountPaid(purchaseOrdersRequestDTO.getTotalAmountPaid())
                .status(purchaseOrdersRequestDTO.getStatus())
                .SubStatus(purchaseOrdersRequestDTO.getSubStatus())
                .supplier(supplier)
                .build();
        purchaseOrdersRespository.save(purchaseOrders_value);
        var purchaseOrdersId = purchaseOrders_value.getId();
        for (var item : purchaseOrdersRequestDTO.getPurchaseOrderItemsRequestDTO()) {
            purchaseOrderItemsServices.createPurchaseOrderItems(item);
        }
        return purchaseOrdersId;
    }

    @Override
    public void updatePurchaseOrders(Long id, purchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        purchaseOrders purchaseOrders_value = purchaseOrdersRespository.findById(id).orElse(null);
        if (purchaseOrders_value != null) {
            var supplier = supplierRespository.findById(purchaseOrdersRequestDTO.getSupplierId()).orElse(null);
            purchaseOrders_value.setTotalAmount(purchaseOrdersRequestDTO.getTotalAmount());
            purchaseOrders_value.setTotalAmountPaid(purchaseOrdersRequestDTO.getTotalAmountPaid());
            purchaseOrders_value.setStatus(purchaseOrdersRequestDTO.getStatus());
            purchaseOrders_value.setSubStatus(purchaseOrdersRequestDTO.getSubStatus());
            purchaseOrders_value.setSupplier(supplier);
            purchaseOrdersRespository.save(purchaseOrders_value);
            for (var item : purchaseOrdersRequestDTO.getPurchaseOrderItemsRequestDTO()) {
                var poi=purchaseOrderItemsServices.getPurchaseOrderItemsById(id);
                if (poi!= null) {
                    purchaseOrderItemsServices.updatePurchaseOrderItems(poi.getId(), item);
                } else {
                    purchaseOrderItemsServices.createPurchaseOrderItems(item);
                }
            }
        }
    }
    @Override
    public void deletePurchaseOrders(Long id) {
        purchaseOrdersRespository.deleteById(id);
    }
}
