package com.ia.alexander.dto.openAI.response;

import lombok.Data;

@Data
public class ChatCompletionChoice {
    private int index;
    private ChatMessageContent message;
    private String finishReason;
}
