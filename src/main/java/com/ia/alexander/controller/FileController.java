package com.ia.alexander.controller;

import com.ia.alexander.dto.file.request.MultipleFilesDto;
import com.ia.alexander.entity.ConsultationRequest;
import com.ia.alexander.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/upload-multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Subir múltiples archivos y preguntas")
    public ResponseEntity<?> uploadMultipleFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("questions") List<String> questions
    ) {
        if (files == null || files.isEmpty()) {
            return ResponseEntity.badRequest().body("Debe subir al menos un archivo");
        }
        if (questions == null || questions.isEmpty()) {
            return ResponseEntity.badRequest().body("Debe enviar al menos una pregunta");
        }

        // Validar extensiones
        Set<String> formatosPermitidos = Set.of("png", "jpeg", "jpg", "gif", "webp");
        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            if (originalName == null || originalName.isBlank()) {
                return ResponseEntity.badRequest().body("El archivo no tiene nombre válido.");
            }

            String lower = originalName.toLowerCase();
            boolean extensionValida = formatosPermitidos.stream().anyMatch(lower::endsWith);
            if (!extensionValida) {
                return ResponseEntity.badRequest().body(
                        "Formato de archivo no soportado: " + originalName +
                                ". Usa png, jpeg, jpg, gif o webp."
                );
            }
        }

        // Si todo bien
        return ResponseEntity.ok(fileService.uploadMultipleFiles(files, questions));
    }





}
