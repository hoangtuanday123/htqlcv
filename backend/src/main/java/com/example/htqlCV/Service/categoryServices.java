package com.example.htqlCV.Service;

import java.util.List;

import com.example.htqlCV.DAO.request.categoryRequestDTO;
import com.example.htqlCV.Model.category;

public interface categoryServices {
    List<category> getAllCategory();
    category getCategoryById(Long id);
    Long createCategory(categoryRequestDTO category);
    void deleteCategory(Long id);
}
