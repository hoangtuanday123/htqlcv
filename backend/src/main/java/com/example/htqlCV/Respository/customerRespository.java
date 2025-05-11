package com.example.htqlCV.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.customer;

@Repository
public interface  customerRespository extends JpaRepository<customer, Long> {
    
}
