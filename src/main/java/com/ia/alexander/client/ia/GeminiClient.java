package com.ia.alexander.client.ia;

import com.ia.alexander.configuration.DeepseekFeignConfig;
import com.ia.alexander.configuration.GeminiFeignConfig;
import com.ia.alexander.dto.google.request.GeminiRequest;
import com.ia.alexander.dto.google.response.GeminiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "gemini-api", url = "${gemini.api.url}", configuration = GeminiFeignConfig.class)
public interface GeminiClient {

    @PostMapping("/v1beta/models/{modelName}:generateContent")
    GeminiResponse generateContent(
            @PathVariable("modelName") String modelName,
            @RequestBody GeminiRequest request
    );


}
