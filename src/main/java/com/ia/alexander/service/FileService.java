package com.ia.alexander.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileService {
    boolean uploadFile (String bucketName , String key, Path fileLocation);
    Path saveFileInFolder(String destinationFolder, MultipartFile file);
}
