package com.ia.alexander.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "deepseek.api")
public class DeepseekProperties {
    private String key;
    private String url;
}
