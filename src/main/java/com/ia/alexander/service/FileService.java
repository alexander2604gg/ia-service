package com.ia.alexander.service;

import com.ia.alexander.dto.file.request.MultipleFilesDto;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface FileService {
    void uploadMultipleFiles(List<MultipartFile> files);
}
