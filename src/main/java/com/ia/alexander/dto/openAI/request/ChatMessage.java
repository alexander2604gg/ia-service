package com.ia.alexander.dto.openAI.request;

import lombok.Data;
import java.util.List;

@Data
public class ChatMessage {
    private String role;
    private String content;
    private List<ChatMessageContentPart> content2;
}
