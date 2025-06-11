package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.saleOrdersRequestDTO;
import com.example.htqlCV.DAO.response.RevenueByProductAndMonth;
import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.product;
import com.example.htqlCV.Model.saleOrders;
import com.example.htqlCV.Respository.businessRespository;
import com.example.htqlCV.Respository.customerRespository;
import com.example.htqlCV.Respository.productRespository;
import com.example.htqlCV.Respository.saleOrderItemsRespository;
import com.example.htqlCV.Respository.saleOrdersRespository;
import com.example.htqlCV.Service.saleOrderItemsServices;
import com.example.htqlCV.Service.saleOrdersServices;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class saleOrderServiceImpl implements saleOrdersServices{
    private final saleOrdersRespository saleOrdersRespository;
    private final customerRespository customerRespository;
    private final saleOrderItemsServices saleOrderItemsServices;
    private final saleOrderItemsRespository saleOrderItemsRespository;
    private final businessRespository businessRespository;
    private final productRespository productRespository;
    @Override
    public List<saleOrders> getAllSaleOrders(UUID businessId){
        business business_value=businessRespository.findById(businessId).orElse(null);
        return saleOrdersRespository.findByBusiness(business_value);
    }

    @Override
    public saleOrders getSaleOrdersById(UUID id){
        return saleOrdersRespository.findById(id).orElse(null);
    }
    @Override
    public UUID createSaleOrder(saleOrdersRequestDTO saleOrdersRequestDTO){
        var customer=customerRespository.findById(saleOrdersRequestDTO.getCustomerId()).orElse(null);
        business business_value=businessRespository.findById(saleOrdersRequestDTO.getBusinessId()).orElse(null);
        saleOrders saleOrders_value=saleOrders.builder()
        .totalAmount(saleOrdersRequestDTO.getTotalAmount())
        .totalAmountPaid(saleOrdersRequestDTO.getTotalAmountPaid())
        .status(saleOrdersRequestDTO.getStatus())
        .SubStatus(saleOrdersRequestDTO.getSubStatus())
        .qrcodeId(saleOrdersRequestDTO.getQrcodeId())
        .customer(customer)
        .business(business_value)
        .build();
        saleOrdersRespository.save(saleOrders_value);
        var saleOrderId=saleOrders_value.getId();
        for (var item:saleOrdersRequestDTO.getSaleOrderItemsRequestDTO()){
            item.setSaleOrdersId(saleOrderId);
            saleOrderItemsServices.createSaleOrderItems(item);
            if("Completed".equals(saleOrdersRequestDTO.getStatus())){
                product product_value=productRespository.findById(item.getProductId()).orElse(null);
                Long decreaseQuantity=product_value.getStockQuantity()-item.getQuantity();
                product_value.setStockQuantity(decreaseQuantity);
                productRespository.save(product_value);
                
            }
        }
        return saleOrderId;
    } 
    @Override
    public void updateSaleOrder(UUID id,saleOrdersRequestDTO saleOrdersRequestDTO){
        saleOrders saleOrders_value=saleOrdersRespository.findById(id).orElse(null);
        if(saleOrders_value!=null){
            var customer=customerRespository.findById(saleOrdersRequestDTO.getCustomerId()).orElse(null);
            saleOrders_value.setTotalAmount(saleOrdersRequestDTO.getTotalAmount());
            saleOrders_value.setTotalAmountPaid(saleOrdersRequestDTO.getTotalAmountPaid());
            saleOrders_value.setStatus(saleOrdersRequestDTO.getStatus());
            saleOrders_value.setSubStatus(saleOrdersRequestDTO.getSubStatus());
            saleOrders_value.setCustomer(customer);
            saleOrders_value.setQrcodeId(saleOrdersRequestDTO.getQrcodeId());
            saleOrdersRespository.save(saleOrders_value);
            for (var item:saleOrdersRequestDTO.getSaleOrderItemsRequestDTO()){
                item.setSaleOrdersId(id);
                if (item.getId()!=null){
                    saleOrderItemsServices.updateSaleOrderItems(item.getId(), item);
                }
                else{
                    saleOrderItemsServices.createSaleOrderItems(item);
                }
                if("Completed".equals(saleOrdersRequestDTO.getStatus())){
                    product product_value=productRespository.findById(item.getProductId()).orElse(null);
                    Long decreaseQuantity=product_value.getStockQuantity()-item.getQuantity();
                    product_value.setStockQuantity(decreaseQuantity);
                    productRespository.save(product_value);
                }
            }
        }
    }

    @Override
    public void refundSaleOrder(UUID id,saleOrdersRequestDTO saleOrdersRequestDTO){
        if("Refunded".equals(saleOrdersRequestDTO.getStatus())){
            for (var item:saleOrdersRequestDTO.getSaleOrderItemsRequestDTO()){
                product product_value=productRespository.findById(item.getProductId()).orElse(null);
                Long increaseQuantity=product_value.getStockQuantity()+item.getQuantity();
                product_value.setStockQuantity(increaseQuantity);
                productRespository.save(product_value);
            }
            saleOrders saleOrders_value=saleOrdersRespository.findById(id).orElse(null);
            saleOrders_value.setStatus(saleOrdersRequestDTO.getStatus());
            saleOrdersRespository.save(saleOrders_value);
        }
    }
    @Override
    public void deleteSaleOrder(UUID id){
        saleOrders saleOrders_value=saleOrdersRespository.findById(id).orElse(null);
        for(var item:saleOrders_value.getSaleOrderItems()){
            saleOrderItemsRespository.deleteById(item.getId());
        }
        saleOrdersRespository.deleteById(id);
    }
    @Override
    public Long getTotalAmountThisMonthByBusinessId(UUID businessId){
        var total= saleOrdersRespository.getTotalAmountThisMonthByBusinessId(businessId);
        if (total==null){
            return (long) 0;
        }
        return total;
    }
    @Override
    public Long getMonthlyProfitByBusinessId(UUID businessId){
        return saleOrdersRespository.getMonthlyProfitByBusinessId(businessId);
    }

    @Override  
    public List<RevenueByProductAndMonth> getMonthlyRevenueNative(Integer currentYear,UUID businessId){
        return saleOrdersRespository.getMonthlyRevenueNative(currentYear, businessId);
    }
    @Override
    public Long getTotalAmountThisDailyByBusinessId(UUID businessId){
        var total= saleOrdersRespository.getTotalAmountThisDailyByBusinessId(businessId);
        if (total==null){
            return (long) 0;
        }
        return total;
    }
    @Override
    public Long getDailyProfitByBusinessId(UUID businessId){
        var total= saleOrdersRespository.getDailyProfitByBusinessId(businessId);
        if (total==null){
            return (long) 0;
        }
        return total;
    }
}
