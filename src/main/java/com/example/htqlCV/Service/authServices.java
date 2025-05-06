package com.example.htqlCV.Service;

import com.example.htqlCV.DAO.request.authRequestDTO;

public interface authServices {
    String authentication(authRequestDTO authRequestDTO);
    boolean validateToken(String token);
}
