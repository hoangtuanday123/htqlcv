package com.example.htqlCV.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping
    public List<branchProduct> getBranchProductList(@RequestParam UUID businessId) {
        return branchProductServices.getAllBranchProduct(businessId);
    }
    @GetMapping("/{id}")
    public branchProduct getBranchProductById(@PathVariable UUID id) {
        return branchProductServices.getBranchProductById(id);
    }
    @PostMapping("/")
    public UUID createBranchProduct(@RequestBody branchProductRequestDTO branchProduct) {
        System.out.println("branchProduct: " + branchProduct.toString());
        return branchProductServices.createBranchProduct(branchProduct);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteBranchProduct(@PathVariable UUID id) {
        branchProductServices.deleteBranchProduct(id);
    }
}
