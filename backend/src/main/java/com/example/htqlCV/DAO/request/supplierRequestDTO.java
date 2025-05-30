package com.example.htqlCV.DAO.request;

import java.util.UUID;

import lombok.Data;

@Data
public class supplierRequestDTO {
    private String name;
    private String phone;
    private String address;
    private String branch;
    private String mst;
    private String company;
    private String email;
    private String note;
    private UUID businessId;
}
