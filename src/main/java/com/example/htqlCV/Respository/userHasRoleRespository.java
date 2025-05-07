package com.example.htqlCV.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.role;
import com.example.htqlCV.Model.userHasRole;



@Repository
public interface userHasRoleRespository extends JpaRepository<role, Long> {

    void save(userHasRole userHasRole_value);

    
} 