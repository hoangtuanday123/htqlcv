package com.example.htqlCV.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.purchaseOrders;

@Repository
public interface  purchaseOrdersRespository extends JpaRepository<purchaseOrders, Long>{
}
