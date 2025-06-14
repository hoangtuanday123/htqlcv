package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.purchaseOrdersRequestDTO;
import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.product;
import com.example.htqlCV.Model.purchaseOrders;
import com.example.htqlCV.Respository.businessRespository;
import com.example.htqlCV.Respository.productRespository;
import com.example.htqlCV.Respository.purchaseOrderItemRespository;
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
    private final purchaseOrderItemRespository purchaseOrderItemRespository;
    private final businessRespository businessRespository;
    private final productRespository productRespository;
    @Override
    public List<purchaseOrders> getAllPurchaseOrders(UUID businessId) {
        business business_value=businessRespository.findById(businessId).orElse(null);
        return purchaseOrdersRespository.findByBusiness(business_value);
    }
    @Override
    public purchaseOrders getPurchaseOrdersById(UUID id) {
        return purchaseOrdersRespository.findById(id).orElse(null);
    }
    @Override
    public UUID createPurchaseOrders(purchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        var supplier = supplierRespository.findById(purchaseOrdersRequestDTO.getSupplierId()).orElse(null);
        business business_value=businessRespository.findById(purchaseOrdersRequestDTO.getBusinessId()).orElse(null);
        purchaseOrders purchaseOrders_value = purchaseOrders.builder()
                .totalAmount(purchaseOrdersRequestDTO.getTotalAmount())
                .totalAmountPaid(purchaseOrdersRequestDTO.getTotalAmountPaid())
                .status(purchaseOrdersRequestDTO.getStatus())
                .SubStatus(purchaseOrdersRequestDTO.getSubStatus())
                .qrcodeId(purchaseOrdersRequestDTO.getQrcodeId())
                .supplier(supplier)
                .business(business_value)
                .build();
        purchaseOrdersRespository.save(purchaseOrders_value);
        var purchaseOrdersId = purchaseOrders_value.getId();
        for (var item : purchaseOrdersRequestDTO.getPurchaseOrderItemsRequestDTO()) {
            item.setPurchaseOrdersId(purchaseOrdersId);
            purchaseOrderItemsServices.createPurchaseOrderItems(item);
            if("Completed".equals(purchaseOrdersRequestDTO.getStatus())){
                product product_value=productRespository.findById(item.getProductId()).orElse(null);
                Long increaseQuantity=product_value.getStockQuantity()+item.getQuantity();
                product_value.setStockQuantity(increaseQuantity);
                productRespository.save(product_value);
            }
        }
        return purchaseOrdersId;
    }

    @Override
    public void updatePurchaseOrders(UUID id, purchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        purchaseOrders purchaseOrders_value = purchaseOrdersRespository.findById(id).orElse(null);
        if (purchaseOrders_value != null) {
            var supplier = supplierRespository.findById(purchaseOrdersRequestDTO.getSupplierId()).orElse(null);
            purchaseOrders_value.setTotalAmount(purchaseOrdersRequestDTO.getTotalAmount());
            purchaseOrders_value.setTotalAmountPaid(purchaseOrdersRequestDTO.getTotalAmountPaid());
            purchaseOrders_value.setStatus(purchaseOrdersRequestDTO.getStatus());
            purchaseOrders_value.setSubStatus(purchaseOrdersRequestDTO.getSubStatus());
            purchaseOrders_value.setQrcodeId(purchaseOrdersRequestDTO.getQrcodeId());
            purchaseOrders_value.setSupplier(supplier);
            purchaseOrdersRespository.save(purchaseOrders_value);
            for (var item : purchaseOrdersRequestDTO.getPurchaseOrderItemsRequestDTO()) {
                item.setPurchaseOrdersId(id);
                if (item.getId()!= null) {
                    purchaseOrderItemsServices.updatePurchaseOrderItems(item.getId(), item);
                } else {
                    purchaseOrderItemsServices.createPurchaseOrderItems(item);
                }
                if("Completed".equals(purchaseOrdersRequestDTO.getStatus())){
                    product product_value=productRespository.findById(item.getProductId()).orElse(null);
                    Long increaseQuantity=product_value.getStockQuantity()+item.getQuantity();
                    product_value.setStockQuantity(increaseQuantity);
                    productRespository.save(product_value);
                }
            }
        }
    }
    @Override
    public void deletePurchaseOrders(UUID id) {
        purchaseOrders purchaseOrders_value = purchaseOrdersRespository.findById(id).orElse(null);
        for(var item:purchaseOrders_value.getPurchaseOrderItems()){
            purchaseOrderItemRespository.deleteById(item.getId());
        }
        purchaseOrdersRespository.deleteById(id);
    }
    @Override
    public Long getTotalAmountThisMonthByBusinessId(UUID businessId){
        var total= purchaseOrdersRespository.getTotalAmountThisMonthByBusinessId(businessId);
        if (total==null){
            return (long) 0;
        }
        return total;
    }
    @Override
    public void refundPurchaseOrder(UUID id,purchaseOrdersRequestDTO purchaseOrdersRequestDTO){
        if("Refunded".equals(purchaseOrdersRequestDTO.getStatus())){
            for (var item:purchaseOrdersRequestDTO.getPurchaseOrderItemsRequestDTO()){
                product product_value=productRespository.findById(item.getProductId()).orElse(null);
                Long decreaseQuantity=product_value.getStockQuantity()-item.getQuantity();
                product_value.setStockQuantity(decreaseQuantity);
                productRespository.save(product_value);
            }
            purchaseOrders purchaseOrders_value=purchaseOrdersRespository.findById(id).orElse(null);
            purchaseOrders_value.setStatus(purchaseOrdersRequestDTO.getStatus());
            purchaseOrdersRespository.save(purchaseOrders_value);
        }
    }
}
