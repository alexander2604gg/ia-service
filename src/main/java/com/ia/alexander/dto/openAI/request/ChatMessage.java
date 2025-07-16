package com.ia.alexander.dto.openAI.request;

import lombok.Data;
import java.util.List;

@Data
public class ChatMessage {
    private String role;
    private List<ContentItemDTO> content;
}
