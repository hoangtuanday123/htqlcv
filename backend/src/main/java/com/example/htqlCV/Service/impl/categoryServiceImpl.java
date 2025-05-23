package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.categoryRequestDTO;
import com.example.htqlCV.Model.category;
import com.example.htqlCV.Respository.categoryRespository;
import com.example.htqlCV.Service.categoryServices;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class categoryServiceImpl implements categoryServices{
    private final categoryRespository categoryRespository;
    @Override
    public List<category> getAllCategory() {
        return categoryRespository.findAll();
    }
    @Override
    public category getCategoryById(UUID id) {
        return categoryRespository.findById(id).orElse(null);
    }
    @Override
    public UUID createCategory(categoryRequestDTO categoryRequestDTO) {
        category category_value = category.builder()
                .name(categoryRequestDTO.getName())
                .build();
        categoryRespository.save(category_value);
        return category_value.getId();
    }
    @Override
    public void deleteCategory(UUID id) {
        categoryRespository.deleteById(id);
    }
}
