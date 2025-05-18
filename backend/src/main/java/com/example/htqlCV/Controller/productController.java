package com.example.htqlCV.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.productRequestDTO;
import com.example.htqlCV.Model.product;
import com.example.htqlCV.Service.productServices;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class productController {
    private final productServices productService;
    @GetMapping("/")
    public List<product> getProducts() {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public product getproduct(@PathVariable UUID id) {
        var product= productService.getProductById(id);
        return product;
    }
    
    @PostMapping("/")
    public UUID createProduct(@RequestBody productRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO);
    }
    
    @PutMapping("/{id}/update")
    public void updateProduct(@PathVariable UUID id, @RequestBody productRequestDTO productRequestDTO) {
        productService.updateProduct(id, productRequestDTO);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
    
}
