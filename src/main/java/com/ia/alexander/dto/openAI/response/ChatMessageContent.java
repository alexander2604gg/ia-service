package com.ia.alexander.dto.openAI.response;

import lombok.Data;

@Data
public class ChatMessageContent {
    private String role;
    private String content;
}
