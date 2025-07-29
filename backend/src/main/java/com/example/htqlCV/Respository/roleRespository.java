package com.example.htqlCV.Respository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.role;
@Repository
public interface roleRespository extends JpaRepository<role, String> {
    role findByName(String name);
    
}
