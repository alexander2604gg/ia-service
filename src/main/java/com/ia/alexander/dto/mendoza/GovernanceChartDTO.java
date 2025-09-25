package com.ia.alexander.dto.mendoza;

import lombok.Data;

import java.util.List;

@Data
public class GovernanceChartDTO {
    private List<DimensionScore> dimensionScores;
    private String nivelMadurezGlobal;
    private String comentarioGlobal;

    @Data
    public static class DimensionScore {
        private String dimension;
        private int puntaje; // 1 = Inicial, 5 = Optimizado
    }
}