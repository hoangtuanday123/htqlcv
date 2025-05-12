package com.example.htqlCV.Service;

import java.util.List;

import com.example.htqlCV.DAO.request.productRequestDTO;
import com.example.htqlCV.Model.product;

public interface productServices {
    List<product> getAllProduct();
    product getProductById(Long id);
    Long createProduct(productRequestDTO product);
    void updateProduct(Long id, productRequestDTO product);
    void deleteProduct(Long id);
}
