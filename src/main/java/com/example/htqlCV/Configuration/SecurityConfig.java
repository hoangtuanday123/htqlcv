package com.example.htqlCV.Configuration;

import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.htqlCV.Service.authServices;

import lombok.experimental.NonFinal;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @NonFinal
    protected static final String SIGNER_KEY="DMACF1qWXznHLOloAZYRi2UtxXsIv8fZlemlOO8riEGInGO8MFK3+1CQE/STr+K0";
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private customJwtDecoder CustomJwtDecoder;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> request
                .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**","/common/**").permitAll()
                .requestMatchers("/user/**").hasAuthority( "SCOPE_admin")
                .anyRequest().authenticated()
            )
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable();

        http.oauth2ResourceServer(oauth2->oauth2.jwt(jwtConfigurer->jwtConfigurer.decoder(CustomJwtDecoder)));
            

        return http.build();
    }

    // @Bean
    // public JwtDecoder jwtDecoder() {
    //     // Khóa bí mật giống như trong quá trình ký token
    //     SecretKeySpec secretKey = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");

    //     // Cấu hình JwtDecoder với thuật toán HS512
    //     return NimbusJwtDecoder
    //             .withSecretKey(secretKey)  // Cung cấp khóa bí mật
    //             .macAlgorithm(MacAlgorithm.HS512)  // Sử dụng thuật toán HMAC-SHA512
    //             .build();  // Xây dựng JwtDecoder với thuật toán HmacSHA512
    // }

        
}
