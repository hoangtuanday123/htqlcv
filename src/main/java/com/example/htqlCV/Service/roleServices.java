package com.example.htqlCV.Service;

import java.util.List;

import com.example.htqlCV.DAO.request.roleRequestDTO;
import com.example.htqlCV.Model.role;

public interface roleServices {
    role createRole(roleRequestDTO role);
    List<role> getAllRole();
    role getRoleById(Long id);
    role updateRole(Long id, roleRequestDTO role);
    void deleteRole(Long id);
} 