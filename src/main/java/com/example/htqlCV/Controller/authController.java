package com.example.htqlCV.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.authRequestDTO;
import com.example.htqlCV.Service.authServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class authController {
    private final authServices authService;

    @PostMapping("/")
    public String login(@RequestBody authRequestDTO authRequestDTO) {

        String token = authService.authentication(authRequestDTO);
        if (token == null) {
            return "Login failed";
        }
        return token;

    }

}
