package com.example.htqlCV.Respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.purchaseOrderItems;

@Repository
public interface  purchaseOrderItemRespository extends JpaRepository<purchaseOrderItems, Long>{
    List<purchaseOrderItems> findByPurchaseOrders_Id(Long purchaseOrdersId);
}
