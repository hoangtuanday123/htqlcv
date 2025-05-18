package com.example.htqlCV.Respository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.product;

@Repository
public interface productRespository extends JpaRepository<product, UUID>{
    
}
