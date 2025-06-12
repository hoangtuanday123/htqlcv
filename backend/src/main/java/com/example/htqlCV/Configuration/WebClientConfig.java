package com.example.htqlCV.Configuration;
 

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.context.annotation.Configuration;
@Configuration
public class WebClientConfig {
    @Value("${aiService.url}")
    private String aiServiceUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(aiServiceUrl).build();
    }
}
