package com.example.htqlCV.Respository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.branchProduct;
@Repository
public interface branchProductRespository extends JpaRepository<branchProduct, UUID> {
    
}
