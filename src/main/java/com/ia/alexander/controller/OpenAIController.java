package com.ia.alexander.controller;
import com.ia.alexander.dto.ImagenRequestDto;
import com.ia.alexander.service.impl.OpenAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class OpenAIController {

    private final OpenAIService openAIService;

    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/analizar-incidencia")
    public ResponseEntity<String> analizar(@RequestBody ImagenRequestDto request){
        String resultado = openAIService.analizarIncidenciaSeguridad(request);
        return ResponseEntity.ok(resultado);
    }



}
