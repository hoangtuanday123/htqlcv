package com.example.htqlCV.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.changePasswordRequestDTO;
import com.example.htqlCV.DAO.request.userRequestDTO;
import com.example.htqlCV.DAO.response.userResponse;
import com.example.htqlCV.Model.currentUser;
import com.example.htqlCV.Model.user;
import com.example.htqlCV.Service.userSevices;

import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {
   private final userSevices userSevices;


    @PostMapping("/")
    public void createUser(@RequestBody userRequestDTO userRequestDTO) {
        user id=userSevices.createUser(userRequestDTO);
        
    }
    @GetMapping("/")
    public List<userResponse> getAllUser() {
        var authentication=SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getAuthorities());
         // Call the service method to get all users
         List<userResponse> users = userSevices.getAllUser();
         // Return the list of users as a response
         System.out.println("Get all user request received with size: " + users);
        return userSevices.getAllUser();
    }

    @GetMapping("/{id}")
    public userResponse getUser(@PathVariable UUID id) {
        return userSevices.getUserById(id);
    }

    @PutMapping("/{id}/update")
    public void updateUser(@PathVariable UUID id, @RequestBody userRequestDTO userRequestDTO) {
        // System.out.println("Update user request received with id: " + userRequestDTO.getPassword());
        userSevices.updateUser(id, userRequestDTO);
    }
    
    @DeleteMapping("/{id}/delete")
    public void deleteUser(@PathVariable UUID id) {
        userSevices.deleteUser(id);
    }

    @GetMapping("/current_user")
    public currentUser getCurrentUser() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println("Current user: " + username);
            // return null;
            return userSevices.getCurrentUser(username);
        } catch (Exception e) {
            System.out.println("Error retrieving token: " + e.getMessage());
            return null;
        }
        
    }

    @PutMapping("/changePassword")
    public void changePassword(@RequestBody changePasswordRequestDTO changePasswordRequestDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userSevices.changePassword(changePasswordRequestDTO, username);
    }
    
}
