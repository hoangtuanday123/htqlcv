package com.example.htqlCV.Respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.saleOrderItems;
@Repository
public interface  saleOrderItemsRespository extends JpaRepository<saleOrderItems, UUID>{
    List<saleOrderItems> findBySaleOrders_Id(UUID saleOrdersId);
    saleOrderItems findBySaleOrders_IdAndProduct_Id(UUID saleOrdersId, UUID productId);
}
