package com.example.htqlCV.Model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_group_product")
public class category extends abstractEntity {
    @Column(name = "name")
    private String name;
    
    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<product> product;
    
}
