package com.ia.alexander.dto.openAI.response;

import lombok.Data;

@Data
public class ChatCompletionUsage {
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;
}
