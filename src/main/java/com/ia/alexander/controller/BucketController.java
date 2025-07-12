package com.ia.alexander.controller;

import com.ia.alexander.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    @PostMapping
    public ResponseEntity<String> createBucket (@RequestParam String bucketName){
        return ResponseEntity.ok(bucketService.createBucket(bucketName));
    }

}
