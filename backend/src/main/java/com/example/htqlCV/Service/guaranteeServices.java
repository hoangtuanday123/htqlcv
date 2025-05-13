package com.example.htqlCV.Service;

import java.util.List;

import com.example.htqlCV.DAO.request.guaranteeRequestDTO;
import com.example.htqlCV.Model.guarantee;

public interface  guaranteeServices {
    List<guarantee> getAllGuarantee();
    guarantee getGuaranteeById(Long id);
    Long createGuarantee(guaranteeRequestDTO guarantee);
    void deleteGuarantee(Long id);
}
