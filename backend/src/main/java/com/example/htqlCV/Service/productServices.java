package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.productRequestDTO;
import com.example.htqlCV.Model.product;
public interface productServices {
    List<product> getAllProduct(UUID businessId);
    product getProductById(UUID id);
    UUID createProduct(productRequestDTO product);
    void updateProduct(UUID id, productRequestDTO product);
    void deleteProduct(UUID id);
    Integer getTotalStockQuantityByBusinessId(UUID businessId);
}
