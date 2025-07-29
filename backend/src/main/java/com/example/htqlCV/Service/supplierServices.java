package com.example.htqlCV.Service;

import java.util.List;


import com.example.htqlCV.DAO.request.supplierRequestDTO;
import com.example.htqlCV.Model.supplier;
public interface  supplierServices {
    List<supplier> getAllSupplier(String businessId);
    supplier getSupplierById(String id);
    String createSupplier(supplierRequestDTO supplier);
    void updateSupplier(String id, supplierRequestDTO supplier);
    void deleteSupplier(String id);
    Integer countSupplierByBusiness(String businessId);
}
