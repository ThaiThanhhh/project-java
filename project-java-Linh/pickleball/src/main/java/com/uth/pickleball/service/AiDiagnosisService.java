package com.uth.pickleball.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import com.uth.pickleball.model.AnalysisResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AiDiagnosisService {
    private static final Logger logger = LoggerFactory.getLogger(AiDiagnosisService.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String FLASK_API_URL = "http://127.0.0.1:5000/analyze";

    public AnalysisResult analyzeVideo(MultipartFile videoFile) throws IOException {
        // Validate file
        logger.info("Bắt đầu kiểm tra video: {}", videoFile.getOriginalFilename());
        if (videoFile == null || videoFile.isEmpty()) {
            logger.error("Không có file video được chọn");
            throw new IllegalArgumentException("No video file selected");
        }

        String[] allowedExtensions = {".mp4", ".mov", ".avi", ".mkv"};
        boolean validExtension = false;
        for (String ext : allowedExtensions) {
            if (videoFile.getOriginalFilename().toLowerCase().endsWith(ext)) {
                validExtension = true;
                break;
            }
        }
        if (!validExtension) {
            logger.error("Định dạng video không được hỗ trợ: {}", videoFile.getOriginalFilename());
            throw new IllegalArgumentException("Unsupported video format. Use MP4, MOV, AVI, or MKV.");
        }
        if (videoFile.getSize() > 50 * 1024 * 1024) {
            logger.error("Video quá lớn: {} bytes", videoFile.getSize());
            throw new IllegalArgumentException("Video file too large. Maximum size is 50MB.");
        }

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create body with video file
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("video", new ByteArrayResource(videoFile.getBytes()) {
            @Override
            public String getFilename() {
                return videoFile.getOriginalFilename();
            }
        });

        // Create request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = 
            new HttpEntity<>(body, headers);

        try {
            logger.info("Gửi yêu cầu tới Flask API: {}", FLASK_API_URL);
            ResponseEntity<String> response = restTemplate.exchange(
                FLASK_API_URL,
                HttpMethod.POST,
                requestEntity,
                String.class);
                logger.info("Phản hồi từ API - Mã trạng thái: {}", response.getStatusCode());
                logger.debug("Nội dung phản hồi API: {}", response.getBody());
                JsonNode root = objectMapper.readTree(response.getBody());
                double averagePerformance = root.get("average_performance").asDouble();
                logger.info("Average Performance: {}", averagePerformance);
                
                // Kiểm tra phản hồi có phải JSON hợp lệ không
                if (response.getBody() == null || response.getBody().isEmpty()) {
                    logger.error("Phản hồi từ API rỗng");
                    throw new IOException("Empty response from Flask API");
                }
                
            return objectMapper.readValue(response.getBody(), AnalysisResult.class);
        } catch (Exception e) {
            logger.error("Lỗi khi gọi Flask API: {}", e.getMessage(), e);
            throw new IOException("Failed to analyze video: " + e.getMessage(), e);
        }
    }
}