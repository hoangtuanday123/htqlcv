package com.example.htqlCV.Service;



import java.util.List;


import com.example.htqlCV.DAO.request.changePasswordRequestDTO;
import com.example.htqlCV.DAO.request.userRequestDTO;
import com.example.htqlCV.DAO.response.userResponse;
import com.example.htqlCV.Model.currentUser;
import com.example.htqlCV.Model.user;
public interface  userSevices {
    user createUser(userRequestDTO userRequestDTO);
    user createStaff(userRequestDTO userRequestDTO);
    List<userResponse> getUsersByBusiness(String businessId);
    List<userResponse> getAllUser();
    userResponse getUserById(String id);
    void updateUser(String id, userRequestDTO userRequestDTO);
    void deleteUser(String id);
    currentUser getCurrentUser(String username);
    void changePassword(changePasswordRequestDTO changePasswordRequestDTO, String username);
    Integer countUserByBusiness(String businessId);
}
