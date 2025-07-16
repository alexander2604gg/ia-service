package com.ia.alexander.dto.openAI.request;

import lombok.Data;

@Data
public class ImageContent {
    private String type;
    private ImageUrl image_url;
}
