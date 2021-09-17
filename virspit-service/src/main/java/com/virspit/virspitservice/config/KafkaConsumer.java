package com.virspit.virspitservice.config;

import com.virspit.virspitservice.domain.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    @KafkaListener(topics = "product", groupId = "listing")
    public void listenProductGroup(ProductDto productDto) throws IOException {
        System.out.println("Consumer Message is : " + productDto);
    }

}
