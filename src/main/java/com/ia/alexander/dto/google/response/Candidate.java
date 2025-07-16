package com.ia.alexander.dto.google.response;

import com.ia.alexander.dto.google.request.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidate {
    private Content content; // El contenido generado por el modelo
    private String finishReason; // Razón por la que el modelo terminó de generar (ej. "STOP", "MAX_TOKENS")
    private int index; // Índice del candidato
    private List<SafetyRating> safetyRatings; // Puntuaciones de seguridad para la respuesta
}