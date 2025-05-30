package com.example.htqlCV.Respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.customer;
import com.example.htqlCV.Model.purchaseOrders;
@Repository
public interface  purchaseOrdersRespository extends JpaRepository<purchaseOrders, UUID>{

    List<purchaseOrders> findByBusiness(business business);
}
