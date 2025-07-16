package com.ia.alexander.service.impl;

import com.ia.alexander.dto.file.request.MultipleFilesDto;
import com.ia.alexander.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final S3Client s3Client;

    @Override
    public void uploadMultipleFiles(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            String key = generateKeyWithOriginalExtension(file);
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket("security-api-5245432")
                    .contentDisposition("inline")
                    .key(key)
                    .build();

            try {
                s3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException("Error to upload files to S3", e);
            }
        }

    }

    public String generateKeyWithOriginalExtension(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        return UUID.randomUUID() + extension;
    }


}
