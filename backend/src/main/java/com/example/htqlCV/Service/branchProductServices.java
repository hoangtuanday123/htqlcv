package com.example.htqlCV.Service;

import java.util.List;

import com.example.htqlCV.DAO.request.branchProductRequestDTO;
import com.example.htqlCV.Model.branchProduct;
public interface branchProductServices {
    List<branchProduct> getAllBranchProduct();
    branchProduct getBranchProductById(Long id);
    Long createBranchProduct(branchProductRequestDTO branchProduct);
    void deleteBranchProduct(Long id);
}
