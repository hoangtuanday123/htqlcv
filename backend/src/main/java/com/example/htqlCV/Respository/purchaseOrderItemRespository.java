package com.example.htqlCV.Respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.purchaseOrderItems;
@Repository
public interface  purchaseOrderItemRespository extends JpaRepository<purchaseOrderItems, UUID>{
    List<purchaseOrderItems> findByPurchaseOrders_Id(UUID purchaseOrdersId);
    purchaseOrderItems findByPurchaseOrders_IdAndProduct_Id(UUID purchaseOrdersId, UUID productId);
}
