package com.example.htqlCV.Respository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.saleOrderItems;
@Repository
public interface  saleOrderItemsRespository extends JpaRepository<saleOrderItems, String>{
    List<saleOrderItems> findBySaleOrders_Id(String saleOrdersId);
    saleOrderItems findBySaleOrders_IdAndProduct_Id(String saleOrdersId, String productId);
}
