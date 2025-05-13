package com.example.htqlCV.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_guarantee")
public class guarantee extends abstractEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "guaranteeTime")
    private Integer guaranteeTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private product product;
}
