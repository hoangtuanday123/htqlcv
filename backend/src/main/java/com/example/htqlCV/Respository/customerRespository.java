package com.example.htqlCV.Respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.customer;
@Repository
public interface  customerRespository extends JpaRepository<customer, UUID> {
    List<customer> findByBusiness(business business);
    Integer countCustomerByBusiness(business business);
}
