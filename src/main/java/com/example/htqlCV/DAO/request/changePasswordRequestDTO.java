package com.example.htqlCV.DAO.request;

import lombok.Data;

@Data
public class changePasswordRequestDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
