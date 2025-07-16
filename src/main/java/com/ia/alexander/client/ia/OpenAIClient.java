package com.ia.alexander.client.ia;

import com.ia.alexander.configuration.OpenAIClientConfig;
import com.ia.alexander.dto.openAI.request.ChatCompletionRequest;
import com.ia.alexander.dto.openAI.response.ChatCompletionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "openai-client", url = "${openai.api.url}", configuration = OpenAIClientConfig.class)
public interface OpenAIClient {

    @PostMapping("/chat/completions")
    ChatCompletionResponse chat(@RequestBody ChatCompletionRequest request);
}
