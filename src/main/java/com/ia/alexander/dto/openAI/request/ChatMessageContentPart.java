package com.ia.alexander.dto.openAI.request;

import lombok.Data;

@Data
public class ChatMessageContentPart {
    private String type;
    private String text;
    private ImageUrl imageUrl;
}
