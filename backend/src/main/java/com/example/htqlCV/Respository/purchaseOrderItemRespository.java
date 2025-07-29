package com.example.htqlCV.Respository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.purchaseOrderItems;
@Repository
public interface  purchaseOrderItemRespository extends JpaRepository<purchaseOrderItems, String>{
    List<purchaseOrderItems> findByPurchaseOrders_Id(String purchaseOrdersId);
    purchaseOrderItems findByPurchaseOrders_IdAndProduct_Id(String purchaseOrdersId, String productId);
}
