package com.example.htqlCV.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.supplierRequestDTO;
import com.example.htqlCV.Model.supplier;
import com.example.htqlCV.Respository.supplierRespository;
import com.example.htqlCV.Service.supplierServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class supplierServiceImpl implements supplierServices {
    private final supplierRespository supplierRespository;

    @Override
    public List<supplier> getAllSupplier() {
        return supplierRespository.findAll();
    }

    @Override
    public supplier getSupplierById(Long id) {
        return supplierRespository.findById(id).orElse(null);
    }

    @Override
    public Long createSupplier(supplierRequestDTO supplierRequestDTO) {
        supplier supplier_value = supplier.builder()
                .name(supplierRequestDTO.getName())
                .address(supplierRequestDTO.getAddress())
                .phone(supplierRequestDTO.getPhone())
                .branch(supplierRequestDTO.getBranch())
                .email(supplierRequestDTO.getEmail())
                .company(supplierRequestDTO.getCompany())
                .note(supplierRequestDTO.getNote())
                .build();
        supplierRespository.save(supplier_value);
        return supplier_value.getId();
    }

    @Override
    public void updateSupplier(Long id, supplierRequestDTO supplierRequestDTO) {
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
    public void deleteSupplier(Long id) {
        supplierRespository.deleteById(id);
    }
    
}
