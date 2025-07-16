package com.ia.alexander.service.impl;

import com.ia.alexander.dto.ImagenRequestDto;
import com.ia.alexander.entity.ConsultationRequest;
import com.ia.alexander.entity.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenAIService {

    private final ChatClient chatClient;

    public OpenAIService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String analizarIncidenciaSeguridad(ImagenRequestDto requestDto) {
        // Validaciones
        if (requestDto == null || requestDto.getUrls() == null || requestDto.getUrls().isEmpty()) {
            throw new IllegalArgumentException("Se requieren URLs de imágenes de la incidencia");
        }
        if (requestDto.getQuestions() == null || requestDto.getQuestions().isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos una pregunta técnica");
        }

        // 1. Preparar imágenes
        List<Media> imagenes = requestDto.getUrls().stream()
                .map(url -> new Media(MimeTypeUtils.IMAGE_JPEG, URI.create(url)))
                .toList();

        // 2. Construir prompt con contexto fijo + preguntas dinámicas
        String contextoFijo = """
            Estas imágenes muestran una incidencia de seguridad industrial.
            Responde de forma técnica y concisa.
            Preguntas específicas a responder:
            """;

        String preguntas = String.join("\n- ", requestDto.getQuestions()); // Formato con viñetas

        String promptCompleto = contextoFijo + "\n- " + preguntas;

        // 3. Enviar a la IA
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(promptCompleto)
                        .media(imagenes.toArray(new Media[0]))
                )
                .call()
                .content();
    }

    public String analizarIncidenciaSeguridad(ConsultationRequest request) {
        // Validación básica
        if (request == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        if (request.getImages() == null || request.getImages().isEmpty()) {
            throw new IllegalArgumentException("Se requieren imágenes de la incidencia");
        }

        if (request.getQuestions() == null || request.getQuestions().isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos una pregunta técnica");
        }

        // 1️⃣ Preparar imágenes como Media[]
        List<Media> imagenes = request.getImages().stream()
                .map(img -> new Media(MimeTypeUtils.IMAGE_JPEG, URI.create(img.getImageUrl())))
                .toList();

        // 2️⃣ Construir prompt con contexto fijo y preguntas dinámicas
        String contextoFijo = """
            Estas imágenes muestran una incidencia de seguridad industrial.
            Responde de forma técnica y concisa.
            Preguntas específicas a responder:
            """;

        String preguntas = request.getQuestions().stream()
                .map(Question::getQuestionText)
                .map(q -> "- " + q)
                .collect(Collectors.joining("\n"));

        String promptCompleto = contextoFijo + "\n" + preguntas;

        // 3️⃣ Enviar a la IA
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(promptCompleto)
                        .media(imagenes.toArray(new Media[0]))
                )
                .call()
                .content();
    }


}
