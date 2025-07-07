package com.ia.alexander.dto.openAI.request;

import lombok.Data;
import java.util.List;

@Data
public class ChatCompletionRequest {
    private String model;
    private List<ChatMessage> messages;
}
