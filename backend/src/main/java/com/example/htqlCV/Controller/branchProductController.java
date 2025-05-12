package com.example.htqlCV.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.branchProductRequestDTO;
import com.example.htqlCV.Model.branchProduct;
import com.example.htqlCV.Service.branchProductServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/branchProduct")
@RequiredArgsConstructor
public class branchProductController {
    private final branchProductServices branchProductServices;
    @GetMapping("/")
    public List<branchProduct> getBranchProductList() {
        return branchProductServices.getAllBranchProduct();
    }
    @GetMapping("/{id}")
    public branchProduct getBranchProductById(Long id) {
        return branchProductServices.getBranchProductById(id);
    }
    @PostMapping("/")
    public Long createBranchProduct(branchProductRequestDTO branchProduct) {
        return branchProductServices.createBranchProduct(branchProduct);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteBranchProduct(Long id) {
        branchProductServices.deleteBranchProduct(id);
    }
}
