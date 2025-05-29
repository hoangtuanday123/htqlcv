package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

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
    public business getBusinessById(UUID id){
        return businessRespository.findById(id).orElse(null);
    }

    @Override
    public UUID createBusiness(businessRequestDTO businessRequestDTO){
        business business_value=business.builder()
        .name(businessRequestDTO.getName())
        .email(businessRequestDTO.getEmail())
        .mst(businessRequestDTO.getMst())
        .build();
        businessRespository.save(business_value);
        return business_value.getId();
    }

    @Override
    public void updateBusiness(UUID id,businessRequestDTO businessRequestDTO){
        business business_value=businessRespository.findById(id).orElse(null);
        if(business_value!=null){
            business_value.setName(businessRequestDTO.getName());
            business_value.setEmail(businessRequestDTO.getEmail());
            business_value.setMst(businessRequestDTO.getMst());
            businessRespository.save(business_value);
        }
    }
    @Override
    public void blockBusiness(UUID id){
        business business_value=businessRespository.findById(id).orElse(null);
        if(business_value!=null){
            business_value.setIsBlocked(true);
        }
    }

    @Override
    public void openBlockBusiness(UUID id){
        business business_value=businessRespository.findById(id).orElse(null);
        if(business_value!=null){
            business_value.setIsBlocked(false);
        }
    }
}
