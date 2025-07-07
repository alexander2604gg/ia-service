package com.ia.alexander.dto.openAI.response;
import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatCompletionChoice> choices;
    private ChatCompletionUsage usage;
}
