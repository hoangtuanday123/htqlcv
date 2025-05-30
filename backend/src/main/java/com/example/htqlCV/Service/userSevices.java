package com.example.htqlCV.Service;



import java.util.List;
import java.util.UUID;

import com.example.htqlCV.DAO.request.changePasswordRequestDTO;
import com.example.htqlCV.DAO.request.userRequestDTO;
import com.example.htqlCV.DAO.response.userResponse;
import com.example.htqlCV.Model.currentUser;
import com.example.htqlCV.Model.user;
public interface  userSevices {
    user createUser(userRequestDTO userRequestDTO);
    List<userResponse> getAllUser();
    userResponse getUserById(UUID id);
    void updateUser(UUID id, userRequestDTO userRequestDTO);
    void deleteUser(UUID id);
    currentUser getCurrentUser(String username);
    void changePassword(changePasswordRequestDTO changePasswordRequestDTO, String username);
}
