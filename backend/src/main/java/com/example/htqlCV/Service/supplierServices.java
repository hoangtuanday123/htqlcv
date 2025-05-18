package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.supplierRequestDTO;
import com.example.htqlCV.Model.supplier;
public interface  supplierServices {
    List<supplier> getAllSupplier();
    supplier getSupplierById(UUID id);
    UUID createSupplier(supplierRequestDTO supplier);
    void updateSupplier(UUID id, supplierRequestDTO supplier);
    void deleteSupplier(UUID id);
}
