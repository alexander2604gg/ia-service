package com.ia.alexander.service.impl;

import com.ia.alexander.dto.file.request.MultipleFilesDto;
import com.ia.alexander.entity.ConsultationRequest;
import com.ia.alexander.service.ConsultationRequestService;
import com.ia.alexander.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final ConsultationRequestService consultationRequestService;
    private final S3Client s3Client;

    @Override
    public ConsultationRequest uploadMultipleFiles(List<MultipartFile> files, List<String> questions) {
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            String key = generateKeyWithOriginalExtension(file);
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket("security-api-5245432")
                    .contentDisposition("inline")
                    .key(key)
                    .build();

            try {
                s3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));

                // Construye la URL usando el cliente S3
                String url = s3Client.utilities().getUrl(
                        GetUrlRequest.builder()
                                .bucket("security-api-5245432")
                                .key(key)
                                .build()
                ).toString();

                urls.add(url);
            } catch (IOException e) {
                throw new RuntimeException("Error to upload files to S3", e);
            }
        }
        return consultationRequestService.save(urls , questions);
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
