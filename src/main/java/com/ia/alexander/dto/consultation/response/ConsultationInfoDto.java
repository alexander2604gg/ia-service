package com.ia.alexander.dto.consultation.response;

import com.ia.alexander.entity.Image;
import com.ia.alexander.entity.Question;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ConsultationInfoDto {
    private LocalDateTime createdAt;
    private String aiResponse;
    private List<Question> questions;
    private List<Image> images;
}
