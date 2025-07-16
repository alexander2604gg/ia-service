package com.ia.alexander.configuration;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GeminiFeignConfig {

    private final GeminiProperties geminiProperties;

    @Bean
    public RequestInterceptor geminiAiInterceptor() {
        return requestTemplate -> requestTemplate.header(
                "Authorization", "Bearer " + geminiProperties.getKey()
        );
    }

}

