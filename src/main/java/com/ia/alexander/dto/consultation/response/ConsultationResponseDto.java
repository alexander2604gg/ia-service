package com.ia.alexander.dto.consultation.response;

import com.ia.alexander.entity.Question;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConsultationResponseDto {
    private Long consultationRequestId;
    private LocalDateTime createdAt;
    private List<Question> questions;
}
