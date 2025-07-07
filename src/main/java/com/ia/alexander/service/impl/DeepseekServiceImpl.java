package com.ia.alexander.service.impl;

import com.ia.alexander.client.ia.DeepseekClient;
import com.ia.alexander.dto.openAI.request.ChatCompletionRequest;
import com.ia.alexander.dto.openAI.response.ChatCompletionResponse;
import com.ia.alexander.service.DeepseekService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeepseekServiceImpl implements DeepseekService {

    private final DeepseekClient deepseekClient;

    @Override
    public ChatCompletionResponse analyze(ChatCompletionRequest chatCompletionRequest) {
        return deepseekClient.chatCompletion(chatCompletionRequest);
    }
}
