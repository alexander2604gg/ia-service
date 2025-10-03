package com.ia.alexander.service.impl;

import com.ia.alexander.dto.ImagenRequestDto;
import com.ia.alexander.dto.mendoza.GovernanceChartDTO;
import com.ia.alexander.dto.mendoza.GovernanceEvaluationRequest;
import com.ia.alexander.dto.mendoza.RoadmapDTO;
import com.ia.alexander.dto.tesis.ModelBatchRequestDto;
import com.ia.alexander.dto.tesis.ModelRequestDto;
import com.ia.alexander.dto.tesis.PredictBatchResponseDto;
import com.ia.alexander.dto.tesis.PredictResponseDto;
import com.ia.alexander.entity.ConsultationRequest;
import com.ia.alexander.entity.LoteCultivo;
import com.ia.alexander.entity.Question;
import com.ia.alexander.repository.LoteCultivoRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenAIService {

    private final ChatClient chatClient;
    private final LoteCultivoRepository loteCultivoRepository;

    public PredictBatchResponseDto predictBatch(ModelBatchRequestDto requestDto) {
        if (requestDto == null || requestDto.getTexts() == null || requestDto.getTexts().isEmpty()) {
            throw new IllegalArgumentException("La lista de textos no puede estar vacía");
        }

        // 📝 Construimos un prompt que contenga todos los textos
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("""
            Vas a actuar como un modelo de clasificación de texto para ver si un post tiene depresion o no

            Para cada uno de los siguientes textos, debes devolver una respuesta JSON con este formato exacto:

            [
              {
                "redditId": "id del texto",
                "text": "texto original",
                "prediction": {
                  "label": "EtiquetaPredicha",
                  "score": 0.0
                }
              }
            ]

            - El campo "label" debe contener LABEL_0 (No tuvo depresion), LABEL_1 (Si tiene depresion)
            - El campo "score" debe ser un número entre 0 y 1 que represente la confianza.
            - NO expliques nada. NO uses markdown. Devuelve SOLO el JSON válido.
            
            Lista de textos:
            """);

        for (ModelRequestDto textDto : requestDto.getTexts()) {
            promptBuilder.append("- ID: ").append(textDto.getRedditId())
                    .append("\n  Texto: ").append(textDto.getText()).append("\n\n");
        }

        String prompt = promptBuilder.toString();

        // 🚀 Llamamos al modelo de ChatGPT
        String rawResponse = chatClient.prompt()
                .user(userSpec -> userSpec.text(prompt))
                .call()
                .content();

        // 🧼 Sanitizamos la respuesta (igual que en tu otro ejemplo)
        rawResponse = rawResponse
                .replaceAll("(?s)```json", "")
                .replaceAll("(?s)```", "")
                .trim();

        int start = rawResponse.indexOf("[");
        int end = rawResponse.lastIndexOf("]");
        if (start >= 0 && end > start) {
            rawResponse = rawResponse.substring(start, end + 1);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            // 🧠 Parseamos la respuesta a la lista de predicciones
            List<PredictResponseDto> predictions = mapper.readValue(
                    rawResponse,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, PredictResponseDto.class)
            );

            PredictBatchResponseDto responseDto = new PredictBatchResponseDto();
            responseDto.setPredictions(predictions);
            return responseDto;

        } catch (Exception e) {
            throw new RuntimeException("Error al parsear la respuesta de la IA. Respuesta cruda: " + rawResponse, e);
        }
    }

    public RoadmapDTO generarRoadmap(String analisisTexto) {
        if (analisisTexto == null || analisisTexto.isBlank()) {
            throw new IllegalArgumentException("El análisis de madurez no puede estar vacío");
        }

        String prompt = """
        Tienes el siguiente análisis de Gobernanza de TI:

        %s

        Quiero que transformes este análisis en un JSON que represente un roadmap de mejora
        estilo tablero Kanban (como ClickUp o Trello). 

        El JSON debe tener el siguiente formato:

        {
          "etapas": [
            {
              "nombre": "Corto plazo",
              "tareas": [
                { "titulo": "Texto de la tarea", "estado": "Pendiente | En progreso | Completado" }
              ]
            },
            {
              "nombre": "Mediano plazo",
              "tareas": [ ... ]
            },
            {
              "nombre": "Largo plazo",
              "tareas": [ ... ]
            }
          ]
        }

        - Genera al menos 2 tareas por etapa.
        - Usa estados variados (Pendiente, En progreso, Completado).
        - Devuélveme ÚNICAMENTE el JSON válido.
        - No uses backticks, no uses bloques de markdown.
        - No expliques nada.
        """.formatted(analisisTexto);

        String respuestaJson = chatClient.prompt()
                .user(userSpec -> userSpec.text(prompt))
                .call()
                .content();

        // 🔧 Sanitizar (igual que en generarReporteGraficable)
        respuestaJson = respuestaJson
                .replaceAll("(?s)```json", "")
                .replaceAll("(?s)```", "")
                .trim();

        int start = respuestaJson.indexOf("{");
        int end = respuestaJson.lastIndexOf("}");
        if (start >= 0 && end > start) {
            respuestaJson = respuestaJson.substring(start, end + 1);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(respuestaJson, RoadmapDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear respuesta de la IA a RoadmapDTO. Respuesta cruda: "
                    + respuestaJson, e);
        }
    }


    public GovernanceChartDTO generarReporteGraficable(String analisisTexto) {
        if (analisisTexto == null || analisisTexto.isBlank()) {
            throw new IllegalArgumentException("El análisis de madurez no puede estar vacío");
        }

        String prompt = """
        Tienes el siguiente análisis de Gobernanza de TI:

        %s

        Quiero que transformes este análisis en un JSON ESTRICTO con este formato:

        {
          "dimensionScores": [
            { "dimension": "Gestión de riesgos", "puntaje": 1-5 },
            { "dimension": "Alineación estratégica", "puntaje": 1-5 },
            { "dimension": "Roles y responsabilidades", "puntaje": 1-5 },
            { "dimension": "Métricas de desempeño", "puntaje": 1-5 },
            { "dimension": "Optimización continua", "puntaje": 1-5 }
          ],
          "nivelMadurezGlobal": "Inicial | Repetible | Definido | Gestionado | Optimizado",
          "comentarioGlobal": "Texto breve"
        }

        - Usa puntajes numéricos de 1 a 5 según el nivel de madurez de cada dimensión.
        - Devuélveme ÚNICAMENTE el JSON, sin texto adicional.
        - Responde ÚNICAMENTE con JSON válido.
        - No uses backticks, no uses bloques de markdown.
        - No expliques nada.
        """.formatted(analisisTexto);

        String respuestaJson = chatClient.prompt()
                .user(userSpec -> userSpec.text(prompt))
                .call()
                .content();

        // 🔧 Sanitizar respuesta para evitar errores con backticks o texto extra
        respuestaJson = respuestaJson
                .replaceAll("(?s)```json", "")
                .replaceAll("(?s)```", "")
                .trim();

        // En caso de que devuelva texto antes/después del JSON, recortamos
        int start = respuestaJson.indexOf("{");
        int end = respuestaJson.lastIndexOf("}");
        if (start >= 0 && end > start) {
            respuestaJson = respuestaJson.substring(start, end + 1);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(respuestaJson, GovernanceChartDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear respuesta de la IA a GovernanceChartDTO. Respuesta cruda: "
                    + respuestaJson, e);
        }
    }

    public String evaluarMadurezGobernanza(GovernanceEvaluationRequest request) {
        if (request == null || request.getRespuestas() == null || request.getRespuestas().isEmpty()) {
            throw new IllegalArgumentException("Debe enviar al menos una respuesta de Gobernanza de TI");
        }

        // Contexto fijo
        String contextoFijo = """
        Eres un experto en Gobernanza de TI.
        Debes analizar las respuestas proporcionadas y determinar el nivel de madurez de la organización en TI.
        Usa un enfoque basado en niveles de madurez (Inicial, Repetible, Definido, Gestionado, Optimizado).
        Proporciona un análisis claro, con observaciones y una conclusión del nivel de madurez alcanzado.
        
        Aquí están las preguntas y las respuestas:
        """;

        // Construir el bloque con preguntas y respuestas
        String respuestas = request.getRespuestas().stream()
                .map(r -> "- Pregunta: " + r.getPregunta() + "\n  Respuesta: " + r.getRespuesta())
                .collect(Collectors.joining("\n\n"));

        String promptCompleto = contextoFijo + "\n\n" + respuestas;

        // Enviar a la IA
        return chatClient.prompt()
                .user(userSpec -> userSpec.text(promptCompleto))
                .call()
                .content();
    }


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
