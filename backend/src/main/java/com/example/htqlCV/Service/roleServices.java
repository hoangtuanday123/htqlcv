package com.example.htqlCV.Service;

import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.roleRequestDTO;
import com.example.htqlCV.Model.role;
public interface roleServices {
    role createRole(roleRequestDTO role);
    List<role> getAllRole();
    role getRoleById(UUID id);
    role getRoleByName(String Name);
    role updateRole(UUID id, roleRequestDTO role);
    void deleteRole(UUID id);
} 