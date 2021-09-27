package com.virspit.virspitproduct.domain.product.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaOrderConsumer {
    @KafkaListener(topics = "${kafka.topic.order}")
    public void consumeOrder(String productId) {
        log.info("Ordered product id={}", productId);
    }
}
