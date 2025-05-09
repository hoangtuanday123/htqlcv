package com.example.htqlCV.Configuration;

import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.example.htqlCV.Service.authServices;

import lombok.experimental.NonFinal;
@Component

public class customJwtDecoder implements JwtDecoder{
    @NonFinal
    protected static final String SIGNER_KEY="DMACF1qWXznHLOloAZYRi2UtxXsIv8fZlemlOO8riEGInGO8MFK3+1CQE/STr+K0";

    @Autowired
    private  authServices authService;
    private NimbusJwtDecoder nimbusJwtDecoder=null;

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            
            var response = authService.validateToken(token);

            if (!response) throw new JwtException("Token invalid");
        } 
        catch (Exception e){
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
