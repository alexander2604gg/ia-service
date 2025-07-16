package com.ia.alexander.service;

import com.ia.alexander.entity.ConsultationRequest;

import java.util.List;

public interface ConsultationRequestService {

    ConsultationRequest save (List<String> urls, List<String> questions);

}
