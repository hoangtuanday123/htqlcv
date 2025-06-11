package com.example.htqlCV.Service;

import java.util.UUID;

import com.example.htqlCV.DAO.request.authRequestDTO;
import com.example.htqlCV.DAO.request.invalidTokenRequest;
import com.example.htqlCV.DAO.request.refreshTokenDTO;
import com.example.htqlCV.DAO.request.reserPasswordRequestDTO;

public interface authServices {
    String authentication(authRequestDTO authRequestDTO);
    boolean validateToken(String token);
    void logout(invalidTokenRequest invalidTokenRequest);
    String refreshToken(refreshTokenDTO refreshTokenDTO);
    void forgotPassword(String email);
    void resetPassword(reserPasswordRequestDTO reserPasswordRequestDTO);
    String getToken(UUID verification_id, String token);
}
