package com.virspit.virspitorder.service;

import com.virspit.virspitorder.dto.response.OrdersResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaOrderProducer {
    private final KafkaTemplate<String, OrdersResponseDto> kafkaTemplate;

    public void sendOrder(final String topic, final OrdersResponseDto ordersResponseDto) {
        log.info("send order={}", ordersResponseDto);
        kafkaTemplate.send(topic, ordersResponseDto);
    }

}
