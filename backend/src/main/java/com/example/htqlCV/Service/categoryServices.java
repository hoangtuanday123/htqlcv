package com.example.htqlCV.Service;

import java.util.List;


import com.example.htqlCV.DAO.request.categoryRequestDTO;
import com.example.htqlCV.Model.category;

public interface categoryServices {
    List<category> getAllCategory(String businessId);
    category getCategoryById(String id);
    String createCategory(categoryRequestDTO category);
    void deleteCategory(String id);
}
