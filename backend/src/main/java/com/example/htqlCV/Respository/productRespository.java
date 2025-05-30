package com.example.htqlCV.Respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.product;

@Repository
public interface productRespository extends JpaRepository<product, UUID>{
    List<product> findByBusiness(business business);
}
