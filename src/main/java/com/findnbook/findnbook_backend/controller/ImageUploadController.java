package com.findnbook.findnbook_backend.controller;

import com.findnbook.findnbook_backend.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/upload")
@CrossOrigin(origins = "*")
public class ImageUploadController {

    private final S3Service s3Service;

    public ImageUploadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/public/business-logo")
    public ResponseEntity<Map<String, String>> uploadLogo(@RequestParam("file") MultipartFile file) {
        try {
            String url = s3Service.uploadFile(file, "business-logos");
            return ResponseEntity.ok(Map.of("url", url));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
