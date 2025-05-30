package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.branchProductRequestDTO;
import com.example.htqlCV.Model.branchProduct;
public interface branchProductServices {
    List<branchProduct> getAllBranchProduct(UUID businessId);
    branchProduct getBranchProductById(UUID id);
    UUID createBranchProduct(branchProductRequestDTO branchProduct);
    void deleteBranchProduct(UUID id);
}
