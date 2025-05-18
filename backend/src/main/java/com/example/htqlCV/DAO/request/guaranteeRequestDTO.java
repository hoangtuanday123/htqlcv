package com.example.htqlCV.DAO.request;

import java.util.UUID;

import lombok.Data;
@Data
public class guaranteeRequestDTO {
    private String name;
    private Integer guaranteeTime;
    private UUID productId;
}
