package com.example.htqlCV.Service;

import java.util.List;


import com.example.htqlCV.DAO.request.branchProductRequestDTO;
import com.example.htqlCV.Model.branchProduct;
public interface branchProductServices {
    List<branchProduct> getAllBranchProduct(String businessId);
    branchProduct getBranchProductById(String id);
    String createBranchProduct(branchProductRequestDTO branchProduct);
    void deleteBranchProduct(String id);
}
