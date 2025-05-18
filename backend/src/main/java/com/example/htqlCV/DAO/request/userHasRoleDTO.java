package com.example.htqlCV.DAO.request;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;
@Data
public class userHasRoleDTO implements Serializable {
    private UUID userId;
    private UUID roleId;
    
}
