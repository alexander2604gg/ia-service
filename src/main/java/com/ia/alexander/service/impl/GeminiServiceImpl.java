package com.ia.alexander.service.impl;

import com.ia.alexander.client.ia.GeminiClient;
import com.ia.alexander.dto.google.request.Content;
import com.ia.alexander.dto.google.request.GeminiRequest;
import com.ia.alexander.dto.google.request.InlineData;
import com.ia.alexander.dto.google.request.Part;
import com.ia.alexander.dto.google.response.Candidate;
import com.ia.alexander.dto.google.response.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl {

    private final GeminiClient geminiClient;

    public String generateTextFromImage(String textPrompt, byte[] imageData, String mimeType) {
        String base64Image = Base64.getEncoder().encodeToString(imageData);

        Part textPart = new Part(textPrompt);
        Part imagePart = new Part(new InlineData(mimeType, base64Image));
        Content content = new Content(List.of(textPart, imagePart)); // List.of() para Java 9+
        GeminiRequest requestBody = new GeminiRequest(Collections.singletonList(content));

        try {

            GeminiResponse response = geminiClient.generateContent("gemini-1.5-pro", requestBody);
            return extractTextFromResponse(response);
        } catch (Exception e) {
            return "Error al procesar imagen: " + e.getMessage();
        }
    }

    private String extractTextFromResponse(GeminiResponse response) {
        if (response != null && response.getCandidates() != null && !response.getCandidates().isEmpty()) {
            Candidate firstCandidate = response.getCandidates().get(0);
            if (firstCandidate.getContent() != null && firstCandidate.getContent().getParts() != null && !firstCandidate.getContent().getParts().isEmpty()) {
                StringBuilder generatedText = new StringBuilder();
                for (Part part : firstCandidate.getContent().getParts()) {
                    if (part.getText() != null) {
                        generatedText.append(part.getText());
                    }
                }
                return generatedText.toString();
            }
        }
        return "No se pudo obtener respuesta del modelo.";
    }

    public String generateTextOnly(String textPrompt) {
        Part textPart = new Part(textPrompt);
        Content content = new Content(List.of(textPart));
        GeminiRequest requestBody = new GeminiRequest(Collections.singletonList(content));

        try {
            GeminiResponse response = geminiClient.generateContent(
                    "gemini-pro",
                    requestBody
            );
            return extractTextFromResponse(response);
        } catch (Exception e) {
            return "Error al procesar texto: " + e.getMessage();
        }
    }



}
