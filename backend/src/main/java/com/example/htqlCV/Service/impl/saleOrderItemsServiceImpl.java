package com.example.htqlCV.Service.impl;

import java.util.List;

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
    private saleOrderItemsRespository saleOrderItemsRespository;
    private productRespository productRespository;
    private saleOrdersRespository saleOrdersRespository;
    @Override
    public List<saleOrderItems> getSaleOrderItemsBySaleOrder(Long saleOrdersId) {
        return saleOrderItemsRespository.findBySaleOrders_Id(saleOrdersId);
    }

    @Override
    public  saleOrderItems getSaleOrderItemsById(Long id){
        return saleOrderItemsRespository.findById(id).orElse(null);
    }

    @Override
    public saleOrderItems getSaleOrderItemsByProductIDSaleOrder(Long saleOrdersId, Long productId){
        return saleOrderItemsRespository.findBySaleOrders_IdAndProduct_Id(saleOrdersId, productId);
    }

    @Override
    public Long createSaleOrderItems(saleOrderItemsRequestDTO saleOrderItemsRequestDTO){
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
    public void updateSaleOrderItems(Long id, saleOrderItemsRequestDTO saleOrderItemsRequestDTO){
        saleOrderItems saleOrderItems_value=saleOrderItemsRespository.findById(id).orElse(null);
        if (saleOrderItems_value != null) {
            saleOrderItems_value.setQuantity(saleOrderItemsRequestDTO.getQuantity());
            saleOrderItems_value.setUnitPrice(saleOrderItemsRequestDTO.getUnitPrice());
            saleOrderItems_value.setNote(saleOrderItemsRequestDTO.getNote());
            saleOrderItemsRespository.save(saleOrderItems_value);
        }
    }
    @Override
    public void deleteSaleOrderItems(Long id){
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
