package com.example.htqlCV.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.userRequestDTO;
import com.example.htqlCV.Model.user;
import com.example.htqlCV.Respository.userRepository;
import com.example.htqlCV.Service.userSevices;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class userServiceImpl implements userSevices {
    private final userRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public long createUser(userRequestDTO userRequestDTO) {

        user user_value = user.builder()
                .username(userRequestDTO.getUsername())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .email(userRequestDTO.getEmail())
                .phoneNumber(userRequestDTO.getPhoneNumber())
                .address(userRequestDTO.getAddress())
                .build(); 
        userRepository.save(user_value);
        return user_value.getId();
    }

    @Override
    public List<user> getAllUser() {
        return userRepository.findAll();
    }
    
}
