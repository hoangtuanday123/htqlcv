package com.example.htqlCV.Service.impl;

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
    
}
