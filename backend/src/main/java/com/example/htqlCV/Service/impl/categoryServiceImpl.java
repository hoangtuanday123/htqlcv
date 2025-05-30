package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.categoryRequestDTO;
import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.category;
import com.example.htqlCV.Respository.businessRespository;
import com.example.htqlCV.Respository.categoryRespository;
import com.example.htqlCV.Service.categoryServices;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class categoryServiceImpl implements categoryServices{
    private final categoryRespository categoryRespository;
    private final businessRespository businessRespository;
    @Override
    public List<category> getAllCategory(UUID businessId) {
        business business_value=businessRespository.findById(businessId).orElse(null);
        return categoryRespository.findByBusiness(business_value);
    }
    @Override
    public category getCategoryById(UUID id) {
        return categoryRespository.findById(id).orElse(null);
    }
    @Override
    public UUID createCategory(categoryRequestDTO categoryRequestDTO) {
        business business_value=businessRespository.findById(categoryRequestDTO.getBusinessId()).orElse(null);
        category category_value = category.builder()
                .name(categoryRequestDTO.getName())
                .business(business_value)
                .build();
        categoryRespository.save(category_value);
        return category_value.getId();
    }
    @Override
    public void deleteCategory(UUID id) {
        categoryRespository.deleteById(id);
    }
}
