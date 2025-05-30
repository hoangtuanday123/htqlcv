package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.supplierRequestDTO;
import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.supplier;
import com.example.htqlCV.Respository.businessRespository;
import com.example.htqlCV.Respository.supplierRespository;
import com.example.htqlCV.Service.supplierServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class supplierServiceImpl implements supplierServices {
    private final supplierRespository supplierRespository;
    private final businessRespository businessRespository;
    @Override
    public List<supplier> getAllSupplier(UUID businessId) {
        business business_value=businessRespository.findById(businessId).orElse(null);
        return supplierRespository.findByBusiness(business_value);
    }

    @Override
    public supplier getSupplierById(UUID id) {
        return supplierRespository.findById(id).orElse(null);
    }

    @Override
    public UUID createSupplier(supplierRequestDTO supplierRequestDTO) {
        business business_value=businessRespository.findById(supplierRequestDTO.getBusinessId()).orElse(null);
        supplier supplier_value = supplier.builder()
                .name(supplierRequestDTO.getName())
                .address(supplierRequestDTO.getAddress())
                .phone(supplierRequestDTO.getPhone())
                .branch(supplierRequestDTO.getBranch())
                .email(supplierRequestDTO.getEmail())
                .company(supplierRequestDTO.getCompany())
                .note(supplierRequestDTO.getNote())
                .business(business_value)
                .build();
        supplierRespository.save(supplier_value);
        return supplier_value.getId();
    }

    @Override
    public void updateSupplier(UUID id, supplierRequestDTO supplierRequestDTO) {
        supplier supplier_value=supplierRespository.findById(id).orElse(null);
        if (supplier_value != null) {
            supplier_value.setName(supplierRequestDTO.getName());
            supplier_value.setAddress(supplierRequestDTO.getAddress());
            supplier_value.setPhone(supplierRequestDTO.getPhone());
            supplier_value.setBranch(supplierRequestDTO.getBranch());
            supplier_value.setEmail(supplierRequestDTO.getEmail());
            supplier_value.setCompany(supplierRequestDTO.getCompany());
            supplier_value.setNote(supplierRequestDTO.getNote());
            supplierRespository.save(supplier_value);
        }
    }

    @Override
    public void deleteSupplier(UUID id) {
        supplierRespository.deleteById(id);
    }
    
}
