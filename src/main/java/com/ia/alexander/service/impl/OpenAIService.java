package com.ia.alexander.service.impl;

import com.ia.alexander.dto.ImagenRequestDto;
import com.ia.alexander.entity.ConsultationRequest;
import com.ia.alexander.entity.LoteCultivo;
import com.ia.alexander.entity.Question;
import com.ia.alexander.repository.LoteCultivoRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OpenAIService {

    private final ChatClient chatClient;
    private final LoteCultivoRepository loteCultivoRepository;

    public OpenAIService(ChatClient chatClient, LoteCultivoRepository loteCultivoRepository) {
        this.chatClient = chatClient;
        this.loteCultivoRepository = loteCultivoRepository;
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

    public String predecirEficienciaLote(LoteCultivo lote) {
        if (lote == null) {
            throw new IllegalArgumentException("El lote no puede ser nulo");
        }

        // 🧠 Prompt con los datos del lote
        String prompt = String.format("""
    Se presentan los datos de un lote agrícola. Por favor, bríndame una predicción precisa sobre el rendimiento del cultivo:
    
    - Edad del cultivo (días): %d
    - Tamaño del lote (ha): %.2f
    - pH del suelo: %.2f
    - Materia orgánica (%%): %.2f
    - Precipitación acumulada (mm): %.2f
    - Temperatura promedio (°C): %.2f
    - Riegos por semana: %.2f
    - Zona: %s
    - Fertilización NPK: %s
    - Uso de tratamiento fitosanitario: %s
    """,
                Optional.ofNullable(lote.getEdadCultivoDias()).orElse(0),
                Optional.ofNullable(lote.getHectareas()).orElse(0.0),
                Optional.ofNullable(lote.getPhSuelo()).orElse(0.0),
                Optional.ofNullable(lote.getMateriaOrganica()).orElse(0.0),
                Optional.ofNullable(lote.getPrecipitacionMm()).orElse(0.0),
                Optional.ofNullable(lote.getTemperaturaPromedioC()).orElse(0.0),
                Optional.ofNullable(lote.getRiegosPorSemana()).orElse(0.0),
                Optional.ofNullable(lote.getZona()).orElse("Desconocida"),
                Optional.ofNullable(lote.getFertilizacionNpk()).orElse("Desconocida"),
                Optional.ofNullable(lote.isUsoTratamientoFitosanitario()).orElse(false) ? "Sí" : "No"
        );

        String answer = chatClient.prompt()
                .user(userSpec -> userSpec.text(prompt))
                .call()
                .content();
        lote.setEficiencia(answer);
        //loteCultivoRepository.save(lote);
        return answer;
    }


}
