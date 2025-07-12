package com.ia.alexander.controller;

import com.ia.alexander.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    @Value("${spring.destination.folder}")
    private String destinationFolder;
    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean uploadFile (@RequestParam String bucketName, @RequestParam String key, @RequestPart MultipartFile file) {
        Path finalPath = fileService.saveFileInFolder(destinationFolder, file);
        return fileService.uploadFile(bucketName,key, finalPath);
    }

}
