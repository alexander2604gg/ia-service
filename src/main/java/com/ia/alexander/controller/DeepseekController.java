package com.ia.alexander.controller;

import com.ia.alexander.dto.openAI.request.ChatCompletionRequest;
import com.ia.alexander.dto.openAI.response.ChatCompletionResponse;
import com.ia.alexander.service.DeepseekService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deepseek")
@RequiredArgsConstructor
public class DeepseekController {
    private final DeepseekService deepseekService;
    @PostMapping("/completions")
    public ResponseEntity<ChatCompletionResponse> createCompletion(@RequestBody ChatCompletionRequest request) {
        ChatCompletionResponse response = deepseekService.analyze(request);
        return ResponseEntity.ok(response);
    }
}
