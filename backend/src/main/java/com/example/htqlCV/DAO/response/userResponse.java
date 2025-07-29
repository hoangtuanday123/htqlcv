package com.example.htqlCV.DAO.response;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class userResponse {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private List<String> roles;
    private String businessId;
}
