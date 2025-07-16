package com.ia.alexander.controller;

import com.ia.alexander.service.ConsultationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/consultation")
@RequiredArgsConstructor
public class ConsultationController {
    private final ConsultationRequestService consultationRequestService;

    @GetMapping
    public ResponseEntity<?> analizar(){
        return ResponseEntity.ok(consultationRequestService.findAllByUser());
    }
}
