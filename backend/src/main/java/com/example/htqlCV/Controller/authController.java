package com.example.htqlCV.Controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.EmailRequest;
import com.example.htqlCV.DAO.request.authRequestDTO;
import com.example.htqlCV.DAO.request.invalidTokenRequest;
import com.example.htqlCV.DAO.request.refreshTokenDTO;
import com.example.htqlCV.DAO.request.reserPasswordRequestDTO;
import com.example.htqlCV.Service.authServices;
import com.example.htqlCV.Service.mailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class authController {
    private final authServices authService;
    private final mailService mailService;
    @PostMapping("/login")
    public String login(@RequestBody authRequestDTO authRequestDTO) throws MessagingException {
        String token = authService.authentication(authRequestDTO);
        if (token == null) {
            return "Login failed";
        }
        // mailService.sendEmail("pnhtuanhcmus@gmail.com", "login", "thank you for login");
        return token;

    }

    @GetMapping("/check_token")
    public String checkToken(@RequestParam String token) {
        boolean a=authService.validateToken(token);
        if (a){
            return "Token is valid";
        }
        return "Token is invalid";
    }
    
    @PostMapping("/logout")
    public void logout(@RequestBody invalidTokenRequest invalidTokenRequest) {
        System.out.println("Logout request received with token: " + invalidTokenRequest.getToken());
        authService.logout(invalidTokenRequest);
    }
    
    @PostMapping("/refresh-token")
    public String refreshToken(@RequestBody refreshTokenDTO refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    @PostMapping("/forgot_password")
    public void forgotPassword(@RequestBody EmailRequest email) throws MessagingException {
        authService.forgotPassword(email.getEmail());
    }
    @GetMapping("/get_token")
    public String getToken(@RequestParam UUID verification_id, @RequestParam String token) {
        return authService.getToken(verification_id, token);
    }

    @PostMapping("/reset_password")
    public void resetPassword(@RequestBody reserPasswordRequestDTO reserPasswordRequestDTO) {
        authService.resetPassword(reserPasswordRequestDTO);
    }
}
