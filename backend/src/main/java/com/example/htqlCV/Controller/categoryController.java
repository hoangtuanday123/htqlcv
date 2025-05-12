package com.example.htqlCV.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.categoryRequestDTO;
import com.example.htqlCV.Model.category;
import com.example.htqlCV.Service.categoryServices;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class categoryController {
    private final categoryServices categoryServices;
    @GetMapping("/")
    public List<category> getCategoryList() {
        return categoryServices.getAllCategory();
    }
    @GetMapping("/{id}")
    public category getCategoryById(Long id) {
        return categoryServices.getCategoryById(id);
    }
    @PostMapping("/")
    public Long createCategory(categoryRequestDTO category) {
        return categoryServices.createCategory(category);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteCategory(Long id) {
        categoryServices.deleteCategory(id);
    }
}
