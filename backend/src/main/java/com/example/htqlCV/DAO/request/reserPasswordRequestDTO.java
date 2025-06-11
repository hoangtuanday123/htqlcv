package com.example.htqlCV.DAO.request;

import java.util.UUID;

import lombok.Data;

@Data
public class reserPasswordRequestDTO {
    private UUID verification_id;
    private String newPassword;
    private String token;
}
