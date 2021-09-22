package com.virspit.virspituser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.groundx.caver_ext_kas.CaverExtKAS;

@Configuration
public class KasConfig {

    @Bean
    public CaverExtKAS caverExtKAS(
            @Value("${kas.chainId}") String chainId,
            @Value("${kas.accessKeyId}") String accessKeyId,
            @Value("${kas.secretAccessKey}") String secretAccessKey) {
        return new CaverExtKAS(chainId, accessKeyId, secretAccessKey);
    }
}
