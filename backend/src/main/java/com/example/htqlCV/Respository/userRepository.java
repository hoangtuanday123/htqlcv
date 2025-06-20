package com.example.htqlCV.Respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.user;
@Repository
public interface  userRepository extends  JpaRepository<user, UUID> {
    List<user> findByBusiness(business business);
    public user findByUsername(String username);
    Integer countUserByBusiness(business business);
    user findByEmail(String email);


}
