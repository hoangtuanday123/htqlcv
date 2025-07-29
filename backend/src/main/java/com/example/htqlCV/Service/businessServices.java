package com.example.htqlCV.Service;

import java.util.List;


import com.example.htqlCV.DAO.request.businessRequestDTO;
import com.example.htqlCV.Model.business;
public interface  businessServices {
    List<business> getBusinesses();
    business getBusinessById(String id);
    String createBusiness(businessRequestDTO businessRequestDTO);
    void blockBusiness(String id);
    void updateBusiness(String id,businessRequestDTO businessRequestDTO);
    void openBlockBusiness(String id);
}
