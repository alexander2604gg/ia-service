package com.ia.alexander.service.impl;

import com.ia.alexander.dto.consultation.response.ConsultationInfoDto;
import com.ia.alexander.dto.consultation.response.ConsultationResponseDto;
import com.ia.alexander.entity.ConsultationRequest;
import com.ia.alexander.entity.Image;
import com.ia.alexander.entity.Question;
import com.ia.alexander.entity.UserSec;
import com.ia.alexander.exception.ResourceNotFoundException;
import com.ia.alexander.repository.ConsultationRequestRepository;
import com.ia.alexander.repository.UserSecRepository;
import com.ia.alexander.service.ConsultationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationRequestServiceImpl implements ConsultationRequestService {

    private final ConsultationRequestRepository consultationRequestRepository;
    private final UserSecRepository userSecRepository;


    @Override
    public ConsultationInfoDto findById (Long id) {
        ConsultationRequest consultationRequest = consultationRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta"));
        return ConsultationInfoDto.builder()
                .aiResponse(consultationRequest.getAiResponse())
                .images(consultationRequest.getImages())
                .questions(consultationRequest.getQuestions())
                .createdAt(consultationRequest.getCreatedAt())
                .build();
    }




}
