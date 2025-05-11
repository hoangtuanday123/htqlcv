package com.example.htqlCV.DAO.request;

import java.sql.Date;

import lombok.Data;

@Data
public class customerRequestDTO {
    private String name;
    private String phone;
    private Date dob;
    private String address;
    private String customerType;
    private String mst;
    private String cmnd;
    private String email;
    private String note;
    private String companyName;
}
