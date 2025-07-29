package com.example.htqlCV.Service;

import java.util.List;


import com.example.htqlCV.DAO.request.guaranteeRequestDTO;
import com.example.htqlCV.Model.guarantee;
public interface  guaranteeServices {
    List<guarantee> getGuaranteeByProductId(String id);
    guarantee getGuaranteeById(String id);
    String createGuarantee(guaranteeRequestDTO guarantee);
    void deleteGuarantee(String id);
}
