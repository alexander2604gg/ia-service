package com.ia.alexander.dto.google.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Part {
    private String text;
    private InlineData inlineData;

    public Part(String text) {
        this.text = text;
    }

    public Part(InlineData inlineData) {
        this.inlineData = inlineData;
    }
}