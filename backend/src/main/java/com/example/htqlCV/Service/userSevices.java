package com.example.htqlCV.Service;



import java.util.List;

import com.example.htqlCV.DAO.request.userRequestDTO;
import com.example.htqlCV.DAO.response.userResponse;
import com.example.htqlCV.Model.currentUser;
import com.example.htqlCV.Model.user;


public interface  userSevices {
    user createUser(userRequestDTO userRequestDTO);
    List<userResponse> getAllUser();
    userResponse getUserById(Long id);
    void updateUser(Long id, userRequestDTO userRequestDTO);
    void deleteUser(Long id);
    currentUser getCurrentUser(String username);
}
