package com.example.htqlCV.DAO.request;

import java.io.Serializable;

import lombok.Data;



@Data
public class authRequestDTO implements Serializable {
    private String username;
    private String password;
    
}