package com.ia.alexander.dto.openAI.request;

import lombok.Data;

@Data
public class TextContent {
    private String type; // "text"
    private String text;
}
