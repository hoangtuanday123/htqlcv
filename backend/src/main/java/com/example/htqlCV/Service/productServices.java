package com.example.htqlCV.Service;

import java.util.List;


import com.example.htqlCV.DAO.request.productRequestDTO;
import com.example.htqlCV.Model.product;
public interface productServices {
    List<product> getAllProduct(String businessId);
    product getProductById(String id);
    String createProduct(productRequestDTO product);
    void updateProduct(String id, productRequestDTO product);
    void deleteProduct(String id);
    Integer getTotalStockQuantityByBusinessId(String businessId);
}
