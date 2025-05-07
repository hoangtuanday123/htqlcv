package com.example.htqlCV.Service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.htqlCV.DAO.request.authRequestDTO;
import com.example.htqlCV.Model.user;
import com.example.htqlCV.Respository.userRepository;
import com.example.htqlCV.Service.authServices;
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

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
public class authServiceimpl implements authServices {
    private final userRepository userRepository;
    @NonFinal
    protected static final String SIGNER_KEY="SDyTNfxg+5eE/Y2Hmlp+okKb2+lq2veKPfld8Mw5MTt4KVWPuQYxv4CFhi/7ybd3";
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String authentication(authRequestDTO authRequestDTO) {
        user user_value = userRepository.findByUsername(authRequestDTO.getUsername());

        if (passwordEncoder.matches(authRequestDTO.getPassword(), user_value.getPassword())) {
            String token = generateToken(user_value);
            return token;
        } else {
            return null;
        }
    }
   
    public String generateToken(user user_value) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user_value.getUsername())
                .issuer("your-issuer")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour expiration
                .claim("scope", user_value.getUserRoles())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException ex) {
            throw new RuntimeException("Error signing JWT", ex);
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified=signedJWT.verify(verifier);
            return verified && expirationTime.after(new Date());
        } catch (Exception e) {
            return false;
        }
        
    }
        
}
