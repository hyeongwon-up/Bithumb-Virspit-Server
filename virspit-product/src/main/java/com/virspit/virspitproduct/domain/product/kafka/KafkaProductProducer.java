package com.virspit.virspitproduct.domain.product.kafka;

import com.virspit.virspitproduct.domain.product.dto.response.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProductProducer {
    private final KafkaTemplate<String, ProductResponseDto> kafkaTemplate;

    @Value("${kafka.topic.product}")
    private String productTopicName;

    public void sendProduct(ProductResponseDto productResponseDto) {
        log.info("send product={}", productResponseDto);
        kafkaTemplate.send(productTopicName, productResponseDto);
    }
}
