package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.branchProductRequestDTO;
import com.example.htqlCV.Model.branchProduct;
import com.example.htqlCV.Respository.branchProductRespository;
import com.example.htqlCV.Service.branchProductServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class branchProductServiceImpl implements  branchProductServices {
    private final branchProductRespository branchProductRespository;
    public List<branchProduct> getAllBranchProduct() {
        return branchProductRespository.findAll();
    }

    @Override
    public branchProduct getBranchProductById(UUID id) {
        return branchProductRespository.findById(id).orElse(null);
    }

    @Override
    public UUID createBranchProduct(branchProductRequestDTO branchProductRequestDTO) {
        branchProduct branchProduct_value = branchProduct.builder()
                .name(branchProductRequestDTO.getName())
                .build();
        branchProductRespository.save(branchProduct_value);
        return branchProduct_value.getId();
    }

    @Override
    public void deleteBranchProduct(UUID id) {
        
        branchProductRespository.deleteById(id);
        
        
    }
    
}
