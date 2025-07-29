package com.example.htqlCV.Respository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.business;

@Repository
public interface businessRespository extends JpaRepository<business, String>{
    
}
