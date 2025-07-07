package com.ia.alexander.configuration;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DeepseekFeignConfig {
    private final DeepseekProperties deepseekProperties;

    @Bean
    public RequestInterceptor openAiAuthInterceptor() {
        return requestTemplate -> requestTemplate.header(
                "Authorization", "Bearer " + deepseekProperties.getKey()
        );
    }
}
