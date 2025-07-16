package com.ia.alexander.service.impl;

import com.ia.alexander.dto.consultation.response.ConsultationInfoDto;
import com.ia.alexander.dto.consultation.response.ConsultationResponseDto;
import com.ia.alexander.entity.ConsultationRequest;
import com.ia.alexander.entity.Image;
import com.ia.alexander.entity.Question;
import com.ia.alexander.entity.UserSec;
import com.ia.alexander.exception.ResourceNotFoundException;
import com.ia.alexander.repository.ConsultationRequestRepository;
import com.ia.alexander.repository.ImageRepository;
import com.ia.alexander.repository.UserSecRepository;
import com.ia.alexander.service.ConsultationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ConsultationRequest save(List<String> urls, List<String> questions) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        UserSec userSec = userSecRepository.findUserSecByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Image> images = urls.stream()
                .map(url -> {
                    Image image = new Image();
                    image.setImageUrl(url);
                    return image;
                })
                .collect(Collectors.toList()); // ✅ Mutable list

        List<Question> questionsEntity = questions.stream()
                .map(question -> {
                    Question question1 = new Question();
                    question1.setQuestionText(question);
                    return question1;
                })
                .collect(Collectors.toList()); // ✅ Mutable list

        ConsultationRequest consultationRequest = new ConsultationRequest();
        consultationRequest.setUser(userSec);
        consultationRequest.setCreatedAt(LocalDateTime.now());
        consultationRequest.setImages(images);
        consultationRequest.setQuestions(questionsEntity);
        return consultationRequestRepository.save(consultationRequest);
    }


    @Override
    public List<ConsultationResponseDto> findAllByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        UserSec userSec = userSecRepository.findUserSecByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ConsultationRequest> consultationRequests = consultationRequestRepository.findAllByUser(userSec);

        return consultationRequests.stream()
                .map(request -> {
                    ConsultationResponseDto dto = new ConsultationResponseDto();
                    dto.setConsultationRequestId(request.getConsultationRequestId());
                    dto.setCreatedAt(request.getCreatedAt());
                    dto.setQuestions(request.getQuestions());
                    return dto;
                })
                .collect(Collectors.toList());
    }


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
