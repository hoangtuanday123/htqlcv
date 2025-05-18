package com.example.htqlCV.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "tbl_user_has_role")
public class userHasRole extends abstractEntity{

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private user user;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private role role;

    
}
