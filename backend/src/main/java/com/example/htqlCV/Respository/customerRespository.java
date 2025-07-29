package com.example.htqlCV.Respository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.customer;
@Repository
public interface  customerRespository extends JpaRepository<customer, String> {
    List<customer> findByBusiness(business business);
    Integer countCustomerByBusiness(business business);
}
