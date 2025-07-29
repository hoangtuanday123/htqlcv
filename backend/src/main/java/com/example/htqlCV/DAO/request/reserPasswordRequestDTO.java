package com.example.htqlCV.DAO.request;



import lombok.Data;

@Data
public class reserPasswordRequestDTO {
    private String verification_id;
    private String newPassword;
    private String token;
}
