package com.ia.alexander.dto;


import lombok.Data;

import java.util.List;

@Data
public class ImagenRequestDto{
    private Integer userId;
    private List<String> questions;
    private List<String> urls;
}
