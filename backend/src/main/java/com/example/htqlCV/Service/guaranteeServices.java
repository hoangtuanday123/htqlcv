package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.guaranteeRequestDTO;
import com.example.htqlCV.Model.guarantee;
public interface  guaranteeServices {
    List<guarantee> getGuaranteeByProductId(UUID id);
    guarantee getGuaranteeById(UUID id);
    UUID createGuarantee(guaranteeRequestDTO guarantee);
    void deleteGuarantee(UUID id);
}
