package com.example.htqlCV.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.htqlCV.DAO.request.aiServiceRequest;
import com.example.htqlCV.DAO.response.aiServiceResponse;


@RestController
@RequestMapping("/ai-service")
public class aiServiceController {
    @Autowired
    private WebClient webClient;

    @PostMapping
    public aiServiceResponse getaiservice(@RequestBody aiServiceRequest aiServiceRequest) {
        // String businessId =URLEncoder.encode(aiServiceRequest.getBusinessId(), StandardCharsets.UTF_8) ;
        // String text = URLEncoder.encode(aiServiceRequest.getMessage(), StandardCharsets.UTF_8);
        try 
        {
            return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/")
                .queryParam("business_id", aiServiceRequest.getBusinessId())
                .queryParam("text", aiServiceRequest.getMessage())
                .build())
            .retrieve()
            .bodyToMono(aiServiceResponse.class)
            .block();
        } catch (IllegalArgumentException e) {
            // Handle the exception, e.g., log it or return an error response
            System.err.println("Error: " + e.getMessage());
            aiServiceResponse ai= new aiServiceResponse();
            ai.setMessage("Tìm kiếm không hợp lệ");
            return ai;
        }
        
      
    }
    

}
