package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.categoryRequestDTO;
import com.example.htqlCV.Model.category;

public interface categoryServices {
    List<category> getAllCategory(UUID businessId);
    category getCategoryById(UUID id);
    UUID createCategory(categoryRequestDTO category);
    void deleteCategory(UUID id);
}
