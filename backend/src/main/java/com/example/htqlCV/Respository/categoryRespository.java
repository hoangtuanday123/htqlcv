package com.example.htqlCV.Respository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.category;

@Repository
public interface categoryRespository extends JpaRepository<category, String>{
    List<category> findByBusiness(business business);
}
