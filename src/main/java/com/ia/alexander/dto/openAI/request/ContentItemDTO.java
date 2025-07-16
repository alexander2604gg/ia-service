package com.ia.alexander.dto.openAI.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentItemDTO {
    private String type;
    private String text;
    private ImageUrl image_url;
}
