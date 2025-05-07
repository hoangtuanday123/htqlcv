package com.example.htqlCV.Service;



import java.util.List;

import com.example.htqlCV.DAO.request.userRequestDTO;
import com.example.htqlCV.Model.user;


public interface  userSevices {
    user createUser(userRequestDTO userRequestDTO);
    List<user> getAllUser();
    user getUserById(Long id);
    user updateUser(Long id, userRequestDTO userRequestDTO);
    void deleteUser(Long id);
}
