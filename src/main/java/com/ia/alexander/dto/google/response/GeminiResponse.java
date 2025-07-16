package com.ia.alexander.dto.google.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiResponse {
    private List<Candidate> candidates; // Las posibles respuestas generadas por el modelo
    private PromptFeedback promptFeedback; // Información sobre la seguridad de la entrada
}