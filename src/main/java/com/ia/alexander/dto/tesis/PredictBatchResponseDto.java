package com.ia.alexander.dto.tesis;

import lombok.Data;

import java.util.List;

@Data
public class PredictBatchResponseDto {
    private List<PredictResponseDto> predictions;
}
