package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.businessRequestDTO;
import com.example.htqlCV.Model.business;
public interface  businessServices {
    List<business> getBusinesses();
    business getBusinessById(UUID id);
    UUID createBusiness(businessRequestDTO businessRequestDTO);
    void blockBusiness(UUID id);
    void updateBusiness(UUID id,businessRequestDTO businessRequestDTO);
    void openBlockBusiness(UUID id);
}
