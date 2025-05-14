package com.example.htqlCV.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.guarantee;
import java.util.List;
@Repository
public interface  guaranteeRespository extends JpaRepository<guarantee, Long>{
    List<guarantee> findByProductId(Long productId);
}
