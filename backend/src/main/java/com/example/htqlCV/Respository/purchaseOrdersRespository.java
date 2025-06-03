package com.example.htqlCV.Respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.purchaseOrders;
@Repository
public interface  purchaseOrdersRespository extends JpaRepository<purchaseOrders, UUID>{

    List<purchaseOrders> findByBusiness(business business);
    @Query("""
    SELECT SUM(s.totalAmount)
    FROM purchaseOrders s
    WHERE FUNCTION('MONTH', s.createdAt) = FUNCTION('MONTH', CURRENT_DATE)
      AND FUNCTION('YEAR', s.createdAt) = FUNCTION('YEAR', CURRENT_DATE)
      AND s.business.id = :businessId AND s.status='Completed'
      AND s.isDeleted = false
    """)
    Long getTotalAmountThisMonthByBusinessId(@Param("businessId") UUID businessId);

}
