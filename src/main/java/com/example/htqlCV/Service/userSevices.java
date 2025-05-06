package com.example.htqlCV.Service;



import java.util.List;

import com.example.htqlCV.DAO.request.userRequestDTO;
import com.example.htqlCV.Model.user;


public interface  userSevices {
    long createUser(userRequestDTO userRequestDTO);
    List<user> getAllUser();
}
