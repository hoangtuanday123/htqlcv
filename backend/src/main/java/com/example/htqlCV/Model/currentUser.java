package com.example.htqlCV.Model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class currentUser {
    private UUID id;
    private String email;
    private String username;
    private String phoneNumber;
    private List<String> roles;
    private UUID businessId;
}
