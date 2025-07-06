package com.uth.pickleball.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class AiDiagnosisService {
    private final RestTemplate restTemplate = new RestTemplate();

    public String getDiagnosis(String inputData) {
        String flaskApiUrl = "http://127.0.0.1:5000/"; // Đổi URL nếu cần
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(inputData, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(flaskApiUrl, request, String.class);
        return response.getBody();
    }
}