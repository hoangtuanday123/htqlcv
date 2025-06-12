package com.example.htqlCV.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.htqlCV.DAO.request.aiServiceRequest;
import com.example.htqlCV.DAO.response.aiServiceResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("/ai-service")
public class aiServiceController {
    @Autowired
    private WebClient webClient;

    @PostMapping("/")
    public aiServiceResponse getMethodName(@RequestBody aiServiceRequest aiServiceRequest) {
        String businessId = aiServiceRequest.getBusiness_id();
        String text = aiServiceRequest.getMessage();

        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/")
                .queryParam("business_id", businessId)
                .queryParam("text", text)
                .build())
            .retrieve()
            .bodyToMono(aiServiceResponse.class)
            .block();

    }
    
}
