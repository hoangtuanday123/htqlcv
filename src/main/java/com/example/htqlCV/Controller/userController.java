package com.example.htqlCV.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.userRequestDTO;
import com.example.htqlCV.Model.user;
import com.example.htqlCV.Service.userSevices;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {
   private final userSevices userSevices;


    @PostMapping("/")
    public String createUser(@RequestBody userRequestDTO userRequestDTO) {
        System.out.println("Create user request received with username: " + userRequestDTO.getUsername());
        long id=userSevices.createUser(userRequestDTO);
        return String.valueOf(id);
    }
    @GetMapping("/")
    public List<user> getAllUser() {
        return userSevices.getAllUser();
    }
    
    
}
