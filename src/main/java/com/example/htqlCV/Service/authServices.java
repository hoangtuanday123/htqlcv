package com.example.htqlCV.Service;

import com.example.htqlCV.DAO.request.authRequestDTO;
import com.example.htqlCV.DAO.request.changePasswordRequestDTO;
import com.example.htqlCV.DAO.request.invalidTokenRequest;
import com.example.htqlCV.DAO.request.refreshTokenDTO;

public interface authServices {
    String authentication(authRequestDTO authRequestDTO);
    boolean validateToken(String token);
    void changePassword(changePasswordRequestDTO changePasswordRequestDTO ,String username);
    void logout(invalidTokenRequest invalidTokenRequest);
    String refreshToken(refreshTokenDTO refreshTokenDTO);
}
