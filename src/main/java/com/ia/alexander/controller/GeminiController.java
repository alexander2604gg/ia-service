package com.ia.alexander.controller;

import com.ia.alexander.service.impl.GeminiServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/gemini") // Nueva ruta base para este controlador
public class GeminiController {

    private final GeminiServiceImpl geminiService;

    public GeminiController(GeminiServiceImpl geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping(value = "/vision", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> analyzeImage(
            @RequestParam("prompt") String prompt,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            byte[] imageData = imageFile.getBytes();
            String mimeType = imageFile.getContentType();

            if (mimeType == null || (!mimeType.startsWith("image/jpeg") && !mimeType.startsWith("image/png"))) {
                return ResponseEntity.badRequest().body("Tipo de archivo de imagen no soportado. Se esperan imágenes JPEG o PNG.");
            }

            String response = geminiService.generateTextFromImage(prompt, imageData, mimeType);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la imagen: " + e.getMessage());
        }
    }

    @PostMapping("/text")
    public ResponseEntity<String> generateText(@RequestParam("prompt") String prompt) {
        String response = geminiService.generateTextOnly(prompt);
        return ResponseEntity.ok(response);
    }

}