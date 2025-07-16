package com.ia.alexander.dto.google.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.ConnectException;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {
    private List<Part> parts;
    private String role;

    public Content (List<Part> parts){
        this.parts = parts;
    }
}