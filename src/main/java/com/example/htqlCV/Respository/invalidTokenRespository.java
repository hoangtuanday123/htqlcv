package com.example.htqlCV.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.invalidTokenRedis;

@Repository
public interface  invalidTokenRespository extends CrudRepository<invalidTokenRedis, String> {
    
    
}
