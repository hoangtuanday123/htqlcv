package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.productRequestDTO;
import com.example.htqlCV.Model.branchProduct;
import com.example.htqlCV.Model.category;
import com.example.htqlCV.Model.product;
import com.example.htqlCV.Respository.productRespository;
import com.example.htqlCV.Service.branchProductServices;
import com.example.htqlCV.Service.categoryServices;
import com.example.htqlCV.Service.productServices;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class productServiceImpl implements productServices {
    private final productRespository productRespository;
    private final branchProductServices branchProductServices;
    private final categoryServices categoryServices;
    @Override
    public List<product> getAllProduct() {
        return productRespository.findAll();
    }
    @Override
    public product getProductById(UUID id) {
        return productRespository.findById(id).orElse(null);
    }
    @Override
    public UUID createProduct(productRequestDTO productRequestDTO) {
        branchProduct branchProduct= branchProductServices.getBranchProductById(productRequestDTO.getBranchProductId());
        category category=categoryServices.getCategoryById(productRequestDTO.getCategoryId());
        product product_value;
        product_value = product.builder()
                .name(productRequestDTO.getName())
                .capitalPrice(productRequestDTO.getCapitalPrice())
                .salePrice(productRequestDTO.getSalePrice())
                .stockQuantity(productRequestDTO.getStockQuantity())
                .branchProduct(branchProduct)
                .category(category)
                .build();
        productRespository.save(product_value);
        return product_value.getId();
    }
    @Override
    public void updateProduct(UUID id, productRequestDTO productRequestDTO) {
        product product_value=productRespository.findById(id).orElse(null);
        if (product_value != null) {
            branchProduct branchProduct= branchProductServices.getBranchProductById(productRequestDTO.getBranchProductId());
            category category=categoryServices.getCategoryById(productRequestDTO.getCategoryId());
            product_value.setName(productRequestDTO.getName());
            product_value.setCapitalPrice(productRequestDTO.getCapitalPrice());
            product_value.setSalePrice(productRequestDTO.getSalePrice());
            product_value.setStockQuantity(productRequestDTO.getStockQuantity());
            product_value.setBranchProduct(branchProduct);
            product_value.setCategory(category);
            productRespository.save(product_value);
        }
    }
    @Override
    public void deleteProduct(UUID id) {
        productRespository.deleteById(id);
    }
}
