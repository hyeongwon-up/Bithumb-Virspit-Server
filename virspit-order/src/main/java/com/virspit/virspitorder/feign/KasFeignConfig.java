package com.virspit.virspitorder.feign;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class KasFeignConfig {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
            @Value("${kas.accessKeyId}") String accessKeyId,
            @Value("${kas.secretAccessKey}") String secretAccessKey) {
        return new BasicAuthRequestInterceptor(accessKeyId, secretAccessKey);
    }

    @Bean
    public RequestInterceptor requestInterceptor(@Value("${kas.chainId}") String chainId) {
        return template -> template.header("x-chain-id", chainId);
    }
}