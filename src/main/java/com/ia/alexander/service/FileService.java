package com.ia.alexander.service;

import com.ia.alexander.dto.file.request.MultipleFilesDto;
import com.ia.alexander.entity.ConsultationRequest;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface FileService {
    String uploadMultipleFiles(List<MultipartFile> files, List<String> questions);
}
