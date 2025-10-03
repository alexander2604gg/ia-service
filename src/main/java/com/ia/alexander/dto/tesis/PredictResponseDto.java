package com.ia.alexander.dto.tesis;

import lombok.Data;

@Data public class PredictResponseDto {
    private String redditId;
    private String text;
    private Prediction prediction;
    @Data
    public static class Prediction {
        private String label;
        private double score;
    }
}