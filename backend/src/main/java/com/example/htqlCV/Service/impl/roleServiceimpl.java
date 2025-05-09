package com.example.htqlCV.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.roleRequestDTO;
import com.example.htqlCV.Model.role;
import com.example.htqlCV.Respository.roleRespository;
import com.example.htqlCV.Service.roleServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class roleServiceimpl implements roleServices {
    private final roleRespository roleRespository;
    @Override
    public role createRole(roleRequestDTO roleRequestDTO) {
        role role_value= role.builder()
                .name(roleRequestDTO.getName())
                .build();
        roleRespository.save(role_value);
        return role_value;
    }

    @Override
    public role getRoleById(Long id) {
        return roleRespository.findById(id).orElse(null);
    }

    @Override
    public List<role> getAllRole() {
        return roleRespository.findAll();
    }

    @Override
    public role updateRole(Long id, roleRequestDTO roleRequestDTO) {
        role role_value = roleRespository.findById(id).orElse(null);
        if (role_value != null) {
            role_value.setName(roleRequestDTO.getName());
            roleRespository.save(role_value);
        }
        return role_value;
    }

    @Override
    public void deleteRole(Long id) {
        roleRespository.deleteById(id);
    }
    
}
