package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.customerRequestDTO;
import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.customer;
import com.example.htqlCV.Respository.businessRespository;
import com.example.htqlCV.Respository.customerRespository;
import com.example.htqlCV.Service.customerServices;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class customerServiceImpl implements customerServices {
    private final customerRespository customerRespository;
    private final businessRespository businessRespository;
    @Override
    public List<customer> getCustomers(UUID businessId){
        business business_value=businessRespository.findById(businessId).orElse(null);
        return customerRespository.findByBusiness(business_value);
    }

    @Override
    public customer getCustomer(UUID id){
        return customerRespository.findById(id).orElse(null);
    }

    @Override
    public UUID createCustomer(customerRequestDTO customerRequestDTO){
        business business_value=businessRespository.findById(customerRequestDTO.getBusinessId()).orElse(null);
        customer customer_value=customer.builder()
        .name(customerRequestDTO.getName())
        .phone(customerRequestDTO.getPhone())
        .dob(customerRequestDTO.getDob())
        .address(customerRequestDTO.getAddress())
        .customerType(customerRequestDTO.getCustomerType())
        .mst(customerRequestDTO.getMst())
        .cmnd(customerRequestDTO.getCmnd())
        .email(customerRequestDTO.getEmail())
        .note(customerRequestDTO.getNote())
        .companyName(customerRequestDTO.getCompanyName())
        .business(business_value)
        .build();
        customerRespository.save(customer_value);
        return customer_value.getId();

    }

    @Override
    public void updateCustomer(UUID id,customerRequestDTO customerRequestDTO){
        customer customer_value=customerRespository.findById(id).orElse(null);
        if(customer_value!=null){
            customer_value.setName(customerRequestDTO.getName());
            customer_value.setPhone(customerRequestDTO.getPhone());
            customer_value.setDob(customerRequestDTO.getDob());
            customer_value.setAddress(customerRequestDTO.getAddress());
            customer_value.setCustomerType(customerRequestDTO.getCustomerType());
            customer_value.setMst(customerRequestDTO.getMst());
            customer_value.setCmnd(customerRequestDTO.getCmnd());
            customer_value.setEmail(customerRequestDTO.getEmail());
            customer_value.setNote(customerRequestDTO.getNote());
            customer_value.setCompanyName(customerRequestDTO.getCompanyName());
            customerRespository.save(customer_value);
        }

    }

    @Override
    public void deleteCustomer(UUID id){
        customerRespository.deleteById(id);
    }
}
