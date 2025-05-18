package com.example.htqlCV.Respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.htqlCV.Model.guarantee;
@Repository
public interface  guaranteeRespository extends JpaRepository<guarantee, UUID> {
    List<guarantee> findByProductId(UUID productId);
}
