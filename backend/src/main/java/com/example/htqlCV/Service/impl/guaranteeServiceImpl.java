package com.example.htqlCV.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.guaranteeRequestDTO;
import com.example.htqlCV.Model.guarantee;
import com.example.htqlCV.Model.product;
import com.example.htqlCV.Respository.guaranteeRespository;
import com.example.htqlCV.Respository.productRespository;
import com.example.htqlCV.Service.guaranteeServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class guaranteeServiceImpl implements guaranteeServices{
    private final guaranteeRespository guaranteeRespository;
    private final productRespository productRespository;
    @Override
    public guarantee getGuaranteeById(Long id) {
        return guaranteeRespository.findById(id).orElse(null);
    }

    @Override
    public Long createGuarantee(guaranteeRequestDTO guaranteeRequestDTO) {
        product product_value = productRespository.findById(guaranteeRequestDTO.getProductId()).orElse(null);
        guarantee guarantee_value = guarantee.builder()
                .name(guaranteeRequestDTO.getName())
                .guaranteeTime(guaranteeRequestDTO.getGuaranteeTime())
                .product(product_value)
                .build();
        guaranteeRespository.save(guarantee_value);
        return guarantee_value.getId();
    }

    @Override
    public void deleteGuarantee(Long id) {
        guaranteeRespository.deleteById(id);
    }

    @Override
    public List<guarantee> getGuaranteeByProductId(Long productId) {
        return guaranteeRespository.findByProductId(productId);
    }
}
