package com.ia.alexander.service.impl;

import com.ia.alexander.dto.question.request.QuestionRegisterDto;
import com.ia.alexander.entity.ConsultationRequest;
import com.ia.alexander.entity.Question;
import com.ia.alexander.repository.QuestionRepository;
import com.ia.alexander.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public void save(QuestionRegisterDto questionRegisterDto) {
        Question question = new Question();
        question.setQuestionText(questionRegisterDto.getQuestion());
        ConsultationRequest consultationRequest = new ConsultationRequest();
        consultationRequest.setConsultationRequestId(questionRegisterDto.getConsultationRequestId());
        question.setConsultationRequest(consultationRequest);
        questionRepository.save(question);
    }

}
