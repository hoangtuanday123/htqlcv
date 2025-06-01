package com.example.htqlCV.Service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.changePasswordRequestDTO;
import com.example.htqlCV.DAO.request.userRequestDTO;
import com.example.htqlCV.DAO.response.userResponse;
import com.example.htqlCV.Model.currentUser;
import com.example.htqlCV.Model.role;
import com.example.htqlCV.Model.user;
import com.example.htqlCV.Respository.userRepository;
import com.example.htqlCV.Service.roleServices;
import com.example.htqlCV.Service.userHasRoleServices;
import com.example.htqlCV.Service.userSevices;
import com.example.htqlCV.Model.business;
import java.util.UUID;


import com.example.htqlCV.Respository.businessRespository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class userServiceImpl implements userSevices {
    private final userRepository userRepository;
    private final userHasRoleServices userHasRoleServices;
    private final roleServices roleServices;
    private final businessRespository businessRespository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public user createUser(userRequestDTO userRequestDTO) {
        try {
            System.out.println(userRequestDTO.getBusinessId());
            business business_value=null;
            if(userRequestDTO.getBusinessId()!=null){
                business_value=businessRespository.findById(userRequestDTO.getBusinessId()).orElse(null);
            }
            user user_value = user.builder()
                .username(userRequestDTO.getUsername())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .email(userRequestDTO.getEmail())
                .business(business_value)
                .build(); 
            userRepository.save(user_value);
            
            if (userRequestDTO.getRoles().isEmpty()){
                role role_value=roleServices.getRoleByName("owner");
                userHasRoleServices.createUserHasRole(user_value,role_value);
            }
            for(UUID role:userRequestDTO.getRoles()){
                role role_value=roleServices.getRoleById(role);
                userHasRoleServices.createUserHasRole(user_value,role_value);
            }
            return user_value;
        } catch (Exception e) {
            return null;
        }
        
    }

    @Override
    public user createStaff(userRequestDTO userRequestDTO){
        try {
            System.out.println(userRequestDTO.getBusinessId());
            business business_value=null;
            if(userRequestDTO.getBusinessId()!=null){
                business_value=businessRespository.findById(userRequestDTO.getBusinessId()).orElse(null);
            }
            user user_value = user.builder()
                .username(userRequestDTO.getUsername())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .email(userRequestDTO.getEmail())
                .business(business_value)
                .build(); 
            userRepository.save(user_value);
            
           
            role role_value=roleServices.getRoleByName("staff");
            userHasRoleServices.createUserHasRole(user_value,role_value);
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
            if (user.getBusiness() != null) {
                userResponse.setBusinessId(user.getBusiness().getId());
            } else {
                userResponse.setBusinessId(null); // hoặc không set gì tùy yêu cầu
            }
            List<UUID> roles = new ArrayList<>();
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
    public List<userResponse> getUsersByBusiness(UUID businessId){
        business business_value=businessRespository.findById(businessId).orElse(null);
        var users= userRepository.findByBusiness(business_value);
        var userResponses = new ArrayList<userResponse>();
        for (user user : users) {
            userResponse userResponse = new userResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponse.setAddress(user.getAddress());
            if (user.getBusiness() != null) {
                userResponse.setBusinessId(user.getBusiness().getId());
            } else {
                userResponse.setBusinessId(null); // hoặc không set gì tùy yêu cầu
            }
            userResponses.add(userResponse);
        }
        return userResponses;
        
    }
    @Override
    public userResponse getUserById(UUID id) {
        var user= userRepository.findById(id).orElse(null);
        userResponse userResponse = new userResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setAddress(user.getAddress());
        if (user.getBusiness() != null) {
            userResponse.setBusinessId(user.getBusiness().getId());
        } else {
            userResponse.setBusinessId(null); // hoặc không set gì tùy yêu cầu
        }
        List<UUID> roles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(user.getUserRoles())) {
            user.getUserRoles().forEach(userHasRole -> {
                roles.add(userHasRole.getRole().getId());
            });
        }
        userResponse.setRoles(roles);
        return userResponse;
    }

    @Override
    public void updateUser(UUID id, userRequestDTO userRequestDTO) {
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
    public void deleteUser(UUID id) {
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
        if (user_value.getBusiness() != null) {
            currentuser.setBusinessId(user_value.getBusiness().getId());  
        } else {
            currentuser.setBusinessId(null); // hoặc không set gì tùy yêu cầu
        }
       List<String> roles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(user_value.getUserRoles())) {
            user_value.getUserRoles().forEach(userHasRole -> {
                roles.add(userHasRole.getRole().getName());
            });
        }
        currentuser.setRoles(roles);
        return currentuser;
        
    }
    @Override
    public void changePassword(changePasswordRequestDTO changePasswordRequestDTO, String username) {
        user user_value = userRepository.findByUsername(username);
        if (passwordEncoder.matches(changePasswordRequestDTO.getOldPassword(), user_value.getPassword())
        &&(changePasswordRequestDTO.getNewPassword().equals(changePasswordRequestDTO.getConfirmPassword()))) {
            user_value.setPassword(passwordEncoder.encode(changePasswordRequestDTO.getNewPassword()));
            userRepository.save(user_value);
        } else {
            throw new RuntimeException("Old password is incorrect");
        }
    }
    
}
