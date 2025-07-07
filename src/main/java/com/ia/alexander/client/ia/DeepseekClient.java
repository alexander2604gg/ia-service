package com.ia.alexander.client.ia;

import com.ia.alexander.configuration.DeepseekFeignConfig;
import com.ia.alexander.dto.openAI.request.ChatCompletionRequest;
import com.ia.alexander.dto.openAI.response.ChatCompletionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "deepseekClient", url = "${deepseek.api.url}", configuration = DeepseekFeignConfig.class)
public interface DeepseekClient {
    @PostMapping("/chat/completions")
    ChatCompletionResponse chatCompletion(@RequestBody ChatCompletionRequest request);
}
