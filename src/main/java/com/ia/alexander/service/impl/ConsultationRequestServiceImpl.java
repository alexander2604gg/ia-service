package com.ia.alexander.service.impl;

import com.ia.alexander.entity.ConsultationRequest;
import com.ia.alexander.entity.Image;
import com.ia.alexander.entity.Question;
import com.ia.alexander.entity.UserSec;
import com.ia.alexander.repository.ConsultationRequestRepository;
import com.ia.alexander.repository.ImageRepository;
import com.ia.alexander.repository.UserSecRepository;
import com.ia.alexander.service.ConsultationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationRequestServiceImpl implements ConsultationRequestService {

    private final ImageRepository imageRepository;
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
                .toList();
        List<Question> questionsEntity = questions.stream()
                .map(question -> {
                    Question question1 = new Question();
                    question1.setQuestionText(question);
                    return question1;
                })
                .toList();

        ConsultationRequest consultationRequest = new ConsultationRequest();
        consultationRequest.setUser(userSec);
        consultationRequest.setCreatedAt(LocalDateTime.now());
        consultationRequest.setImages(images);
        consultationRequest.setQuestions(questionsEntity);
        return consultationRequestRepository.save(consultationRequest);
    }




}
