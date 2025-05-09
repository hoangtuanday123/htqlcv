package com.example.htqlCV.DAO.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class userHasRoleDTO implements Serializable {
    private long userId;
    private long roleId;
    
}
