package com.example.htqlCV.Respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.supplier;
@Repository
public interface  supplierRespository extends JpaRepository<supplier, UUID> {
    List<supplier> findByBusiness(business business);
    Integer countSupplierByBusiness(business business);

}
