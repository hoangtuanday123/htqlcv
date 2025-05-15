package com.example.htqlCV.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_supplier")
public class supplier extends abstractEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "branch")
    private String branch;
    @Column(name = "email")
    private String email;
    @Column(name = "company")
    private String company;
    @Column(name = "mst")
    private String mst;
    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "supplier")
    @JsonBackReference
    private List<purchaseOrders> purchaseOrders;
}

