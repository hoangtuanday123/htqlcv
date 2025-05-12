package com.example.htqlCV.Service;

import java.util.List;

import com.example.htqlCV.DAO.request.supplierRequestDTO;
import com.example.htqlCV.Model.supplier;

public interface  supplierServices {
    List<supplier> getAllSupplier();
    supplier getSupplierById(Long id);
    Long createSupplier(supplierRequestDTO supplier);
    void updateSupplier(Long id, supplierRequestDTO supplier);
    void deleteSupplier(Long id);
}
