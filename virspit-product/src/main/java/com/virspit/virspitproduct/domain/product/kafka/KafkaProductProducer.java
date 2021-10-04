package com.virspit.virspitproduct.domain.product.kafka;

import com.virspit.virspitproduct.domain.product.dto.response.ProductKafkaDto;
import com.virspit.virspitproduct.domain.product.dto.response.ProductResponseDto;
import com.virspit.virspitproduct.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProductProducer {
    private final KafkaTemplate<String, ProductKafkaDto> kafkaTemplate;

    @Value("${spring.kafka.topic.product}")
    private String productTopicName;

    public void sendProduct(final ProductKafkaDto productKafkaDto) {
        log.info("send product={}", productKafkaDto);
        kafkaTemplate.send(productTopicName, productKafkaDto);
    }
}
