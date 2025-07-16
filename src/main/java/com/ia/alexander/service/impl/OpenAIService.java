package com.ia.alexander.service.impl;

import com.ia.alexander.dto.ImagenRequestDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.net.URI;
import java.util.List;

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


}
