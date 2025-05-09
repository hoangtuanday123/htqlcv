package com.example.htqlCV.Service;



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

    public void sendEmail(String recipients,String subject,String Content) throws MessagingException{
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8");
        helper.setFrom("pnhtuanhcmus@gmail.com");
        helper.setTo(recipients);
        helper.setSubject(subject);
        helper.setText(Content,true);
        mailSender.send(message);
    }

}
