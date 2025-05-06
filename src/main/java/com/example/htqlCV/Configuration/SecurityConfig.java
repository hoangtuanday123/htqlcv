package com.example.htqlCV.Configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.experimental.NonFinal;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @NonFinal
    protected static final String SIGNER_KEY="SDyTNfxg+5eE/Y2Hmlp+okKb2+lq2veKPfld8Mw5MTt4KVWPuQYxv4CFhi/7ybd3";
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> request
                .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable();

        http.oauth2ResourceServer(oauth2->oauth2.jwt(jwtConfigurer->jwtConfigurer.decoder(jwtDecoder())));
            

        return http.build();
    }

    @Bean 
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder
        .withSecretKey(secretKey)
        .macAlgorithm(MacAlgorithm.HS512).build();
    }
        
}
