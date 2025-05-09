package com.example.htqlCV.DAO.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class userRequestDTO implements Serializable {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private List<Long> roles;
}

