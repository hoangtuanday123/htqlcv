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
    public user createUser(userRequestDTO userRequestDTO) {
        try {
            user user_value = user.builder()
                .username(userRequestDTO.getUsername())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .email(userRequestDTO.getEmail())
                .phoneNumber(userRequestDTO.getPhoneNumber())
                .address(userRequestDTO.getAddress())
                .build(); 
            userRepository.save(user_value);
            return user_value;
        } catch (Exception e) {
            return null;
        }
        
    }

    @Override
    public List<user> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public user getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public user updateUser(Long id, userRequestDTO userRequestDTO) {
        user user_value = userRepository.findById(id).orElse(null);
        if (user_value != null) {
            user_value.setUsername(userRequestDTO.getUsername());
            user_value.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            user_value.setEmail(userRequestDTO.getEmail());
            user_value.setPhoneNumber(userRequestDTO.getPhoneNumber());
            user_value.setAddress(userRequestDTO.getAddress());
            userRepository.save(user_value);
        }
        return user_value;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
}
