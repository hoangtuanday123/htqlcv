package com.example.htqlCV.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.businessRequestDTO;
import com.example.htqlCV.Model.business;
import com.example.htqlCV.Respository.businessRespository;
import com.example.htqlCV.Service.businessServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class businessServiceImpl implements businessServices{
    private final businessRespository businessRespository;
    @Override
    public List<business> getBusinesses(){
        return businessRespository.findAll();
    }

    @Override
    public business getBusinessById(String id){
        return businessRespository.findById(id).orElse(null);
    }

    @Override
    public String createBusiness(businessRequestDTO businessRequestDTO){
        business business_value=business.builder()
        .name(businessRequestDTO.getName())
        .email(businessRequestDTO.getEmail())
        .mst(businessRequestDTO.getMst())
        .address(businessRequestDTO.getAddress())
        .bankAccount(businessRequestDTO.getBankAccount())
        .bankName(businessRequestDTO.getBankName())
        .build();
        businessRespository.save(business_value);
        return business_value.getId();
    }

    @Override
    public void updateBusiness(String id,businessRequestDTO businessRequestDTO){
        business business_value=businessRespository.findById(id).orElse(null);
        if(business_value!=null){
            business_value.setName(businessRequestDTO.getName());
            business_value.setEmail(businessRequestDTO.getEmail());
            business_value.setMst(businessRequestDTO.getMst());
            business_value.setPhone(businessRequestDTO.getPhone());
            business_value.setAddress(businessRequestDTO.getAddress());
            business_value.setBankAccount(businessRequestDTO.getBankAccount());
            business_value.setBankName(businessRequestDTO.getBankName());
            businessRespository.save(business_value);
        }
    }
    @Override
    public void blockBusiness(String id){
        business business_value=businessRespository.findById(id).orElse(null);
        if(business_value!=null){
            business_value.setIsBlocked(true);
        }
    }

    @Override
    public void openBlockBusiness(String id){
        business business_value=businessRespository.findById(id).orElse(null);
        if(business_value!=null){
            business_value.setIsBlocked(false);
        }
    }
}
