package com.ia.alexander.dto.mendoza;

import lombok.Data;

import java.util.List;

@Data
public class GovernanceEvaluationRequest {
    private List<QuestionAnswerDTO> respuestas; // Lista de preguntas y respuestas
}