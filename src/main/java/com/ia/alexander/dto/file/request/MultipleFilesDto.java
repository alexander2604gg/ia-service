package com.ia.alexander.dto.file.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class MultipleFilesDto {
    @NotEmpty(message = "Debes subir al menos un archivo")
    List<MultipartFile> files;
}
