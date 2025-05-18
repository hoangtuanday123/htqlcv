package com.example.htqlCV.Respository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.user;
@Repository
public interface  userRepository extends  JpaRepository<user, UUID> {

    public user findByUsername(String username);
    
    
}
