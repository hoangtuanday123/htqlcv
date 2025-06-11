package com.example.htqlCV.Service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.authRequestDTO;
import com.example.htqlCV.DAO.request.invalidTokenRequest;
import com.example.htqlCV.DAO.request.refreshTokenDTO;
import com.example.htqlCV.DAO.request.reserPasswordRequestDTO;
import com.example.htqlCV.Model.invalidTokenRedis;
import com.example.htqlCV.Model.user;
import com.example.htqlCV.Model.verificationCode;
import com.example.htqlCV.Respository.invalidTokenRespository;
import com.example.htqlCV.Respository.userRepository;
import com.example.htqlCV.Respository.verificationCodeRespository;
import com.example.htqlCV.Service.authServices;
import com.example.htqlCV.Service.mailService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
public class authServiceimpl implements authServices {
    private final userRepository userRepository;
    private final invalidTokenRespository invalidTokenRespository;
    private final verificationCodeRespository verificationCodeRespository;
    private final mailService mailService;
    @NonFinal
    protected static final String SIGNER_KEY="DMACF1qWXznHLOloAZYRi2UtxXsIv8fZlemlOO8riEGInGO8MFK3+1CQE/STr+K0";
    // @Autowired
    // private PasswordEncoder passwordEncoder;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String authentication(authRequestDTO authRequestDTO) {
        user user_value = userRepository.findByUsername(authRequestDTO.getUsername());
        System.out.println("User: " + user_value.getPassword());
        if (passwordEncoder.matches(authRequestDTO.getPassword(), user_value.getPassword())) {
            System.out.println("Login successful for user: " + authRequestDTO.getUsername());
            String token = generateToken(user_value);
            return token;
        } else {
            return null;
        }
       
    }
    
    private String buildScope(user user_value) {
        StringJoiner stringjoiner=new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user_value.getUserRoles())) {
            user_value.getUserRoles().forEach(userHasRole->{
                stringjoiner.add(userHasRole.getRole().getName());
            });
        }
        return stringjoiner.toString();
    }

    public String generateToken(user user_value) {
        // Tạo header JWT với HMAC-SHA512
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
                
    
        // Tạo claims cho JWT
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user_value.getUsername())
                .issuer("your-issuer")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000))  // 1 giờ
                .claim("scope", buildScope(user_value))
                .jwtID(UUID.randomUUID().toString()) // ID duy nhất cho token
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        // Tạo đối tượng JWT và thêm payload
        JWSObject jwsObject = new JWSObject(header,payload);
    
        try {
          
           
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
    
            // Trả về token đã ký dưới dạng chuỗi
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Failed to sign JWT", e);
        }
    }
    

    @Override
    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT=verifyToken(token);
            System.out.println("signedJWT: " + signedJWT);
            if(signedJWT==null){
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }



    private SignedJWT verifyToken(String token){
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified=signedJWT.verify(verifier);
            System.out.println("verified: " + verified);
            if (!verified || expirationTime.before(new Date())){
                return null;
                // throw new RuntimeException("Failed to verify JWT");
            }
            String jti = signedJWT.getJWTClaimsSet().getJWTID();
            if(invalidTokenRespository.existsById(jti)){
               return null;
                // throw new RuntimeException("Token is invalidated");
            }
            return signedJWT;
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify JWT", e);
        }
    }

    private SignedJWT verifyTokenForRefresh(String token){
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            // Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified=signedJWT.verify(verifier);
            System.out.println("verified: " + verified);
            if(!verified ){
                return null;
                // throw new RuntimeException("Failed to verify JWT");
            }
            String jti = signedJWT.getJWTClaimsSet().getJWTID();
            if(invalidTokenRespository.existsById(jti)){
               return null;
                // throw new RuntimeException("Token is invalidated");
            }
            return signedJWT;
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify JWT", e);
        }
    }

    @Override
    public void logout(invalidTokenRequest invalidTokenRequest){
        try {
            var signToken=verifyToken(invalidTokenRequest.getToken());
            String jit=signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime=signToken.getJWTClaimsSet().getExpirationTime();
            invalidTokenRedis tokenEntity = invalidTokenRedis.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
            invalidTokenRespository.save(tokenEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to logout JWT", e);
        }
        
    }

    @Override
    public String refreshToken(refreshTokenDTO refreshTokenDTO) {
        try {
            var signedJWT=verifyTokenForRefresh(refreshTokenDTO.getToken());
            // var jti = signedJWT.getJWTClaimsSet().getJWTID();
            // var expireTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            //logout for token
            // invalidTokenRedis tokenEntity = invalidTokenRedis.builder()
            //     .id(jti)
            //     .expiryTime(expireTime)
            //     .build();
            // invalidTokenRespository.save(tokenEntity);

            var username=signedJWT.getJWTClaimsSet().getSubject();
            user user_value=userRepository.findByUsername(username);
            var newToken=generateToken(user_value);
            return newToken;
        } 
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public void forgotPassword(String email) {
        email = email.toLowerCase();
        user user_value = userRepository.findByEmail(email);
        if (user_value == null) {
            throw new RuntimeException("User not found with email: " + email);
        }
        int EXPIRE_IN_MINUTES = 15;
        Instant now = Instant.now();
        Instant expire = now.plus(Duration.ofMinutes(EXPIRE_IN_MINUTES));
        String token = UUID.randomUUID().toString().replace("-", "");
        verificationCode verificationCode_value = verificationCode.builder()
                .type("forgot_password")
                .code(token)
                .username(user_value.getUsername())
                .phone(user_value.getPhoneNumber())
                .email(email)
                .expirationTime(expire.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime())
                .max_attempts(3)
                .failed_attempts(0)
                .is_active(true)
                .build();
        verificationCodeRespository.save(verificationCode_value);
        try {
            mailService.sendForgotPasswordEmail(
                    user_value.getEmail(),
                    verificationCode_value.getId(),
                    token,
                    EXPIRE_IN_MINUTES
            );
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    verificationCode _get_verification_code(UUID verification_id, String token) {
        verificationCode verificationCode_value = verificationCodeRespository.findById(verification_id).orElse(null);
        if (verificationCode_value == null) {
            throw new RuntimeException("Verification code not found");
        }
        if(verificationCode_value.getExpirationTime().isBefore(java.time.LocalDateTime.now())) {
            throw new RuntimeException("Verification code has expired");
        }
        if(verificationCode_value.getIs_active()== false) {
            throw new RuntimeException("Verification code is not active");
        }
        return verificationCode_value;
    }

    @Override
    public String getToken(UUID verification_id, String token) {
        verificationCode verificationCode_value = _get_verification_code(verification_id, token);
        return verificationCode_value.getCode();
    }
    @Override
    public void resetPassword(reserPasswordRequestDTO reserPasswordRequestDTO){
        verificationCode verificationCode_value = _get_verification_code(reserPasswordRequestDTO.getVerification_id(), reserPasswordRequestDTO.getToken());
        verificationCode_value.setIs_active(true);
        verificationCodeRespository.save(verificationCode_value);
        user user_value = userRepository.findByEmail(verificationCode_value.getEmail());
        user_value.setPassword(passwordEncoder.encode(reserPasswordRequestDTO.getNewPassword()));
        userRepository.save(user_value);
    }
}
