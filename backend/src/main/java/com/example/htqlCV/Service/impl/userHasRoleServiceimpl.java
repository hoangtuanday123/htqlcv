package com.example.htqlCV.Service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.htqlCV.Model.role;
import com.example.htqlCV.Model.user;
import com.example.htqlCV.Model.userHasRole;
import com.example.htqlCV.Respository.userHasRoleRespository;
import com.example.htqlCV.Service.userHasRoleServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class userHasRoleServiceimpl implements userHasRoleServices {
    private final userHasRoleRespository userHasRoleRespository;

    @Override
    public UUID createUserHasRole(user userValue,role roleValue) {
        userHasRole userHasRole_value = userHasRole.builder()
                .user(userValue)
                .role(roleValue)
                .build();
        userHasRoleRespository.save(userHasRole_value);
        return userHasRole_value.getId();
    }
    @Override
    public List<userHasRole> getUserHasRoleByUser(user user) {
        return userHasRoleRespository.findByUser(user);
    }
  
}
