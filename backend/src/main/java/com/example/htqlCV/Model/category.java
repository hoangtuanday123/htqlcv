package com.example.htqlCV.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
    
    @OneToOne(mappedBy = "category")
    private product product;
    
}
