package com.example.htqlCV.Service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.userRequestDTO;
import com.example.htqlCV.DAO.response.userResponse;
import com.example.htqlCV.Model.currentUser;
import com.example.htqlCV.Model.role;
import com.example.htqlCV.Model.user;
import com.example.htqlCV.Respository.userRepository;
import com.example.htqlCV.Service.roleServices;
import com.example.htqlCV.Service.userHasRoleServices;
import com.example.htqlCV.Service.userSevices;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class userServiceImpl implements userSevices {
    private final userRepository userRepository;
    private final userHasRoleServices userHasRoleServices;
    private final roleServices roleServices;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public user createUser(userRequestDTO userRequestDTO) {
        try {
            user user_value = user.builder()
                .username(userRequestDTO.getUsername())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .email(userRequestDTO.getEmail())
                .build(); 
            userRepository.save(user_value);
            for(Long role:userRequestDTO.getRoles()){
                role role_value=roleServices.getRoleById(role);
                userHasRoleServices.createUserHasRole(user_value,role_value);
            }

            return user_value;
        } catch (Exception e) {
            return null;
        }
        
    }

    @Override
    public List<userResponse> getAllUser() {
        var users =userRepository.findAll();
        var userResponses = new ArrayList<userResponse>();
        for (user user : users) {
            userResponse userResponse = new userResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponse.setAddress(user.getAddress());
            List<Long> roles = new ArrayList<>();
            if (!CollectionUtils.isEmpty(user.getUserRoles())) {
                user.getUserRoles().forEach(userHasRole -> {
                    roles.add(userHasRole.getRole().getId());
                });
            }
            userResponse.setRoles(roles);
            userResponses.add(userResponse);
        }
        return userResponses;
    }

    @Override
    public userResponse getUserById(Long id) {
        var user= userRepository.findById(id).orElse(null);
        userResponse userResponse = new userResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setAddress(user.getAddress());
        List<Long> roles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(user.getUserRoles())) {
            user.getUserRoles().forEach(userHasRole -> {
                roles.add(userHasRole.getRole().getId());
            });
        }
        userResponse.setRoles(roles);
        return userResponse;
    }

    @Override
    public void updateUser(Long id, userRequestDTO userRequestDTO) {
        user user_value = userRepository.findById(id).orElse(null);
        if (user_value != null) {
            user_value.setUsername(userRequestDTO.getUsername());
            user_value.setEmail(userRequestDTO.getEmail());
            user_value.setPhoneNumber(userRequestDTO.getPhoneNumber());
            user_value.setAddress(userRequestDTO.getAddress());
            userRepository.save(user_value);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public currentUser getCurrentUser(String username) {
        user user_value = userRepository.findByUsername(username);
        currentUser currentuser = new currentUser();
        currentuser.setId(user_value.getId());
        currentuser.setEmail(user_value.getEmail());
        currentuser.setUsername(user_value.getUsername());
        currentuser.setPhoneNumber(user_value.getPhoneNumber());
       List<String> roles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(user_value.getUserRoles())) {
            user_value.getUserRoles().forEach(userHasRole -> {
                roles.add(userHasRole.getRole().getName());
            });
        }
        currentuser.setRoles(roles);
        return currentuser;
        
    }
    
}
