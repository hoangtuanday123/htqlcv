package com.example.htqlCV.DAO.request;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class verificationCodeRequestDTO {
    private String type;
    private String code;
    private String username;
    private String phone;
    private String email;
    private LocalDateTime expirationTime;
    private Integer max_attempts;
    private Integer failed_attempts;
    private Boolean is_active;
}
