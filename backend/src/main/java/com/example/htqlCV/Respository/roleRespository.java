package com.example.htqlCV.Respository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.htqlCV.Model.role;

@Repository
public interface roleRespository extends JpaRepository<role, Long> {
    
    
}
