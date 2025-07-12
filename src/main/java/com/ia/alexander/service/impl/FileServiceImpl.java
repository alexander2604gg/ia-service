package com.ia.alexander.service.impl;

import com.ia.alexander.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final S3Client s3Client;

    @Override
    public boolean uploadFile(String bucketName, String key, Path fileLocation) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        PutObjectResponse putObjectResponse = s3Client.putObject(objectRequest, fileLocation);
        return putObjectResponse.sdkHttpResponse().isSuccessful();
    }

    @Override
    public Path saveFileInFolder(String destinationFolder, MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isBlank()) {
            throw new IllegalArgumentException("File must have a valid name");
        }
        try {
            Path staticDir = Paths.get(destinationFolder);
            Files.createDirectories(staticDir);
            Path filePath = staticDir.resolve(originalFileName);
            return Files.write(filePath, file.getBytes());

        } catch (IOException e) {
            throw new RuntimeException("Error to save file " + e.getMessage(), e);
        }
    }
}
