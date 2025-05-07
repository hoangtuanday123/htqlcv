package com.example.htqlCV.Controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        user id=userSevices.createUser(userRequestDTO);
        return String.valueOf(id);
    }
    @GetMapping("/")
    public List<user> getAllUser() {
        var authentication=SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getAuthorities());
         // Call the service method to get all users
         List<user> users = userSevices.getAllUser();
         // Return the list of users as a response
         System.out.println("Get all user request received with size: " + users.size());
        return userSevices.getAllUser();
    }

    @GetMapping("/{id}")
    public user getUser(@PathVariable long id) {
        return userSevices.getUserById(id);
    }

    @PutMapping("/update/{id}")
    public user updateUser(@PathVariable long id, @RequestBody userRequestDTO userRequestDTO) {
        return userSevices.updateUser(id, userRequestDTO);
    }
    
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        userSevices.deleteUser(id);
    }
    
}
