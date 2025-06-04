package com.example.htqlCV.Respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.DAO.response.RevenueByProductAndMonth;
import com.example.htqlCV.Model.business;
import com.example.htqlCV.Model.saleOrders;
@Repository
public interface  saleOrdersRespository extends JpaRepository<saleOrders, UUID>{
    List<saleOrders> findByBusiness(business business);
    @Query("""
    SELECT SUM(s.totalAmount)
    FROM saleOrders s
    WHERE FUNCTION('MONTH', s.createdAt) = FUNCTION('MONTH', CURRENT_DATE)
      AND FUNCTION('YEAR', s.createdAt) = FUNCTION('YEAR', CURRENT_DATE)
      AND s.business.id = :businessId AND s.status='Completed'
      AND s.isDeleted = false
    """)
    
    Long getTotalAmountThisMonthByBusinessId(@Param("businessId") UUID businessId);
    @Query(value = """
    SELECT COALESCE(SUM((soi.unit_price - p.capital_price) * soi.quantity), 0)
    FROM tbl_sale_orders so
    JOIN tbl_sale_order_items soi ON soi.sale_orders_id = so.id
    JOIN tbl_product p ON soi.product_id = p.id
    WHERE so.business_id = :businessId AND so.status='Completed'
      AND EXTRACT(MONTH FROM so.created_at) = EXTRACT(MONTH FROM CURRENT_DATE)
      AND EXTRACT(YEAR FROM so.created_at) = EXTRACT(YEAR FROM CURRENT_DATE)
      AND so.is_deleted = false
    """, nativeQuery = true)
    Long getMonthlyProfitByBusinessId(@Param("businessId") UUID businessId);

    @Query(value = """
    SELECT 
        p.name AS productName,
        EXTRACT(MONTH FROM so.created_at) AS month,
        SUM(soi.unit_price * soi.quantity) AS totalRevenue
    FROM 
        tbl_sale_order_items soi
    JOIN 
        tbl_sale_orders so ON soi.sale_orders_id = so.id
    JOIN 
        tbl_product p ON soi.product_id = p.id
    WHERE 
        EXTRACT(YEAR FROM so.created_at) = :currentYear AND so.business_id = :businessId
    GROUP BY 
        p.name, EXTRACT(MONTH FROM so.created_at)
    ORDER BY 
        p.name, EXTRACT(MONTH FROM so.created_at)
  """, nativeQuery = true)
  List<RevenueByProductAndMonth> getMonthlyRevenueNative(@Param("currentYear") int currentYear,@Param("businessId") UUID businessId);


  }
