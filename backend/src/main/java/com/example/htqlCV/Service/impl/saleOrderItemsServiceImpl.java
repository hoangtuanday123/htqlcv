package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.saleOrderItemsRequestDTO;
import com.example.htqlCV.Model.saleOrderItems;
import com.example.htqlCV.Respository.productRespository;
import com.example.htqlCV.Respository.saleOrderItemsRespository;
import com.example.htqlCV.Respository.saleOrdersRespository;
import com.example.htqlCV.Service.saleOrderItemsServices;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class saleOrderItemsServiceImpl implements saleOrderItemsServices{
    private final saleOrderItemsRespository saleOrderItemsRespository;
    private final productRespository productRespository;
    private final saleOrdersRespository saleOrdersRespository;
    @Override
    public List<saleOrderItems> getSaleOrderItemsBySaleOrder(UUID saleOrdersId) {
        return saleOrderItemsRespository.findBySaleOrders_Id(saleOrdersId);
    }

    @Override
    public  saleOrderItems getSaleOrderItemsById(UUID id){
        return saleOrderItemsRespository.findById(id).orElse(null);
    }

    @Override
    public saleOrderItems getSaleOrderItemsByProductIDSaleOrder(UUID saleOrdersId, UUID productId){
        return saleOrderItemsRespository.findBySaleOrders_IdAndProduct_Id(saleOrdersId, productId);
    }

    @Override
    public UUID createSaleOrderItems(saleOrderItemsRequestDTO saleOrderItemsRequestDTO){
        var product=productRespository.findById(saleOrderItemsRequestDTO.getProductId()).orElse(null);
        var saleOrders=saleOrdersRespository.findById(saleOrderItemsRequestDTO.getSaleOrdersId()).orElse(null);
        saleOrderItems saleOrderItems_value=saleOrderItems.builder()
            .saleOrders(saleOrders)
            .product(product)
            .quantity(saleOrderItemsRequestDTO.getQuantity())
            .unitPrice(saleOrderItemsRequestDTO.getUnitPrice())
            .note(saleOrderItemsRequestDTO.getNote())
            .build();
        saleOrderItemsRespository.save(saleOrderItems_value);
        return saleOrderItems_value.getId();
    }

    @Override
    public void updateSaleOrderItems(UUID id, saleOrderItemsRequestDTO saleOrderItemsRequestDTO){
        saleOrderItems saleOrderItems_value=saleOrderItemsRespository.findById(id).orElse(null);
        if (saleOrderItems_value != null) {
            saleOrderItems_value.setQuantity(saleOrderItemsRequestDTO.getQuantity());
            saleOrderItems_value.setUnitPrice(saleOrderItemsRequestDTO.getUnitPrice());
            saleOrderItems_value.setNote(saleOrderItemsRequestDTO.getNote());
            saleOrderItemsRespository.save(saleOrderItems_value);
        }
    }
    @Override
    public void deleteSaleOrderItems(UUID id){
        saleOrderItems item = saleOrderItemsRespository.findById(id)
        .orElseThrow(() -> new RuntimeException("Not found"));

        // Ngắt liên kết để tránh lỗi ràng buộc
        item.setProduct(null);
        item.setSaleOrders(null);

        saleOrderItemsRespository.save(item); // cập nhật trước khi xóa
        saleOrderItemsRespository.delete(item);
        // purchaseOrderItemRespository.deleteById(id);
    }
}
