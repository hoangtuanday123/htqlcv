package com.example.htqlCV.Model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_customer")
public class customer extends abstractEntity{
    @Column(name = "name")
    private String name;
    @Column(name="phone")
    private String phone;
    @Column(name="dob")
    private Date dob;
    @Column(name="address")
    private String address;
    @Column(name = "customerType", nullable = false)
    private String customerType;

    @Column(name="mst")
    private String mst;
    @Column(name="cmnd")
    private String cmnd;
    @Column(name="email")
    private String email;
    @Column(name="note")
    private String note;
    @Column(name="companyName")
    private String companyName;
}

