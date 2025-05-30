package com.example.htqlCV.Respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.user;
import com.example.htqlCV.Model.userHasRole;

@Repository
public interface userHasRoleRespository extends JpaRepository<userHasRole, UUID> {

    // void save(userHasRole userHasRole_value);
    List<userHasRole> findByUser(user user); 
    
} 