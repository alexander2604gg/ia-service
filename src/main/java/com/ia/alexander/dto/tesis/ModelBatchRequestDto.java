package com.ia.alexander.dto.tesis;

import lombok.Data;

import java.util.List;

@Data
public class ModelBatchRequestDto {
    private List<ModelRequestDto> texts;
}
