package com.ia.alexander.service;

import com.ia.alexander.dto.openAI.request.ChatCompletionRequest;
import com.ia.alexander.dto.openAI.response.ChatCompletionResponse;

public interface DeepseekService {
    public ChatCompletionResponse analyze (ChatCompletionRequest chatCompletionRequest);
}
