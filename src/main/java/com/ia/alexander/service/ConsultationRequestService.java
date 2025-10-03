package com.ia.alexander.service;

import com.ia.alexander.dto.consultation.response.ConsultationInfoDto;
import com.ia.alexander.dto.consultation.response.ConsultationResponseDto;
import com.ia.alexander.entity.ConsultationRequest;

import java.util.List;

public interface ConsultationRequestService {

    ConsultationInfoDto findById (Long id);
}
