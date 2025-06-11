package com.example.htqlCV.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "tbl_verification_code")
public class verificationCode extends abstractEntity{
    @Column(name = "type")
    private String type;
    @Column(name = "code")
    private String code;
    @Column(name = "username")
    private String username;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;
    @Column(name = "max_attempts")
    private Integer max_attempts;
    @Column(name = "failed_attempts")
    private Integer failed_attempts;
    @Column(name = "is_active")
    private Boolean is_active;
}
