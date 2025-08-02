package com.example.htqlCV.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.productRequestDTO;
import com.example.htqlCV.Model.branchProduct;
import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.category;
import com.example.htqlCV.Model.product;
import com.example.htqlCV.Respository.businessRespository;
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
    private final businessRespository businessRespository;
    @Override
    public List<product> getAllProduct(String businessId) {
        business business_value=businessRespository.findById(businessId).orElse(null);
        return productRespository.findByBusiness(business_value);
    }
    @Override
    public product getProductById(String id) {
        return productRespository.findById(id).orElse(null);
    }
    @Override
    public String createProduct(productRequestDTO productRequestDTO) {
        branchProduct branchProduct=null;
        if(productRequestDTO.getBranchProductId()!=null){
            branchProduct= branchProductServices.getBranchProductById(productRequestDTO.getBranchProductId());
        }
        category category=null;
        if(productRequestDTO.getCategoryId()!=null){
            category=categoryServices.getCategoryById(productRequestDTO.getCategoryId());
        }
        business business_value=businessRespository.findById(productRequestDTO.getBusinessId()).orElse(null);
        product product_value;
        product_value = product.builder()
                .sku(productRequestDTO.getSku())
                .name(productRequestDTO.getName())
                .capitalPrice(productRequestDTO.getCapitalPrice())
                .salePrice(productRequestDTO.getSalePrice())
                .qrcodeUrl(productRequestDTO.getQrcodeUrl())
                .UnitCalculate(productRequestDTO.getUnitCalculate())
                .branchProduct(branchProduct)
                .stockQuantity(productRequestDTO.getStockQuantity())
                .category(category)
                .business(business_value)
                .build();
        productRespository.save(product_value);
        return product_value.getId();
    }
    @Override
    public void updateProduct(String id, productRequestDTO productRequestDTO) {
        product product_value=productRespository.findById(id).orElse(null);
        if (product_value != null) {
            branchProduct branchProduct=null;
            if(productRequestDTO.getBranchProductId()!=null){
                branchProduct= branchProductServices.getBranchProductById(productRequestDTO.getBranchProductId());
            }
            category category=null;
            if(productRequestDTO.getCategoryId()!=null){
                category=categoryServices.getCategoryById(productRequestDTO.getCategoryId());
            }
            product_value.setSku(productRequestDTO.getSku());
            product_value.setName(productRequestDTO.getName());
            product_value.setCapitalPrice(productRequestDTO.getCapitalPrice());
            product_value.setSalePrice(productRequestDTO.getSalePrice());
            product_value.setQrcodeUrl(productRequestDTO.getQrcodeUrl());
            product_value.setUnitCalculate(productRequestDTO.getUnitCalculate());
            product_value.setBranchProduct(branchProduct);
            product_value.setCategory(category);
            product_value.setStockQuantity(productRequestDTO.getStockQuantity());
            productRespository.save(product_value);
        }
    }
    @Override
    public void deleteProduct(String id) {
        productRespository.deleteById(id);
    }
    @Override
    public Integer getTotalStockQuantityByBusinessId(String businessId){
        return productRespository.getTotalStockQuantityByBusinessId(businessId);
    }
}
