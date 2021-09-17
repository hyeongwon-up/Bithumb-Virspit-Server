package com.virspit.virspitservice.config;

import com.virspit.virspitservice.domain.product.dto.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    @KafkaListener(topics = "product", groupId = "listing")
    public void listenProductGroup(ProductRequestDto productRequestDto) throws IOException {
        System.out.println("Consumer Message is : " + productRequestDto);
    }

}
