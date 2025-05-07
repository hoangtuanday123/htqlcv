package com.example.htqlCV.Service;

import com.example.htqlCV.DAO.request.roleRequestDTO;
import com.example.htqlCV.Model.role;

public interface roleServices {
    role createRole(roleRequestDTO role);
} 