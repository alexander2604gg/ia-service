package com.ia.alexander.controller;
import com.ia.alexander.dto.ImagenRequestDto;
import com.ia.alexander.dto.mendoza.GovernanceChartDTO;
import com.ia.alexander.dto.mendoza.GovernanceEvaluationRequest;
import com.ia.alexander.dto.mendoza.RoadmapDTO;
import com.ia.alexander.entity.LoteCultivo;
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

    @PostMapping("/analizar-mendoza")
    public ResponseEntity<String> analizar(@RequestBody GovernanceEvaluationRequest request) {
        String resultado = openAIService.evaluarMadurezGobernanza(request);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/analizar-mendoza-reporte")
    public ResponseEntity<GovernanceChartDTO> reporte(@RequestBody String request) {
        return ResponseEntity.ok(openAIService.generarReporteGraficable(request));
    }

    @PostMapping("/analizar-mendoza-roadmap")
    public ResponseEntity<RoadmapDTO> roadmap(@RequestBody String request) {
        return ResponseEntity.ok(openAIService.generarRoadmap(request));
    }
    /*@PostMapping("/analizar-incidencia")
    public ResponseEntity<String> analizar(@RequestBody ImagenRequestDto request){
        String resultado = openAIService.analizarIncidenciaSeguridad(request);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/analizar-lote")
    public ResponseEntity<String> analizar(@RequestBody LoteCultivo lote){
        String resultado = openAIService.predecirEficienciaLote(lote);
        return ResponseEntity.ok(resultado);
    }*/



}
