package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.saleOrdersRequestDTO;
import com.example.htqlCV.Model.saleOrders;
import com.example.htqlCV.Respository.customerRespository;
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
    @Override
    public List<saleOrders> getAllSaleOrders(){
        return saleOrdersRespository.findAll();
    }

    @Override
    public saleOrders getSaleOrdersById(UUID id){
        return saleOrdersRespository.findById(id).orElse(null);
    }
    @Override
    public UUID createSaleOrder(saleOrdersRequestDTO saleOrdersRequestDTO){
        var customer=customerRespository.findById(saleOrdersRequestDTO.getCustomerId()).orElse(null);
        saleOrders saleOrders_value=saleOrders.builder()
        .totalAmount(saleOrdersRequestDTO.getTotalAmount())
        .totalAmountPaid(saleOrdersRequestDTO.getTotalAmountPaid())
        .status(saleOrdersRequestDTO.getStatus())
        .SubStatus(saleOrdersRequestDTO.getSubStatus())
        .customer(customer)
        .build();
        saleOrdersRespository.save(saleOrders_value);
        var saleOrderId=saleOrders_value.getId();
        for (var item:saleOrdersRequestDTO.getSaleOrderItemsRequestDTO()){
            item.setSaleOrdersId(saleOrderId);
            saleOrderItemsServices.createSaleOrderItems(item);
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
            saleOrdersRespository.save(saleOrders_value);
            for (var item:saleOrdersRequestDTO.getSaleOrderItemsRequestDTO()){
                item.setSaleOrdersId(id);
                if (item.getId()!=null){
                    saleOrderItemsServices.updateSaleOrderItems(item.getId(), item);
                }
                else{
                    saleOrderItemsServices.createSaleOrderItems(item);
                }
            }
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
}
