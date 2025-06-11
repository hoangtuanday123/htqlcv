package com.example.htqlCV.Service;



import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class mailService {
    private final JavaMailSender mailSender;
    @Value("${frontend.url}")
    private String FRONTEND_URL;
    public void sendEmail(String recipients,String subject,String Content) throws MessagingException{
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8");
        helper.setFrom("pnhtuanhcmus@gmail.com");
        helper.setTo(recipients);
        helper.setSubject(subject);
        helper.setText(Content,true);
        mailSender.send(message);
    }

    public void sendForgotPasswordEmail(String recipients, UUID verification_id, String token,Integer expirationTime) throws MessagingException {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8");
        helper.setFrom("pnhtuanhcmus@gmail.com");
        helper.setTo(recipients);
        String subject = "Hethongquanlybanle- Quên mật khẩu";
        String Content = "Please click <a href='" + FRONTEND_URL + "/reset_password?verification_id=" + verification_id + "&token=" + token + "'>here</a> to reset your password. This link will expire in " + expirationTime + " minutes.";
        helper.setSubject(subject);
        helper.setText(Content,true);
        mailSender.send(message);
    }
}