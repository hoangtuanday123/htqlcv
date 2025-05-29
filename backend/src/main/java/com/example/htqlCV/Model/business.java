package com.example.htqlCV.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name="tbl_business")
public class business extends abstractEntity {
    @Column(name="name")
    private String name;
    @Column(name="mst")
    private String mst;
    @Column(name="email")
    private String email;
    @Column(name="phone")
    private String phone;
    @OneToMany(mappedBy="business")
    @JsonManagedReference
    private List<user> users;
}
