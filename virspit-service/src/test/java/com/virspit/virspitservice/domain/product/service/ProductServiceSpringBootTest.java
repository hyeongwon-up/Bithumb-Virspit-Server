package com.virspit.virspitservice.domain.product.service;

import com.virspit.virspitservice.domain.product.dto.ProductDto;
import com.virspit.virspitservice.domain.product.dto.ProductKafkaDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("서비스 통합 테스트")
@SpringBootTest
class ProductServiceSpringBootTest {

    @Autowired
    private ProductService productService;

    @Rollback
    @DisplayName("insert 테스트")
    @Test
    void insertReal() {
        // given
        ProductKafkaDto kafkaDto = ProductKafkaDto.builder()
                .id(UUID.randomUUID().toString())
                .createdDate(LocalDateTime.now())
                .price(45)
                .build();

        ProductDto dto = ProductDto.builder()
                .id(kafkaDto.getId())
                .createdDate(kafkaDto.getCreatedDate())
                .price(kafkaDto.getPrice())
                .build();
        // when
        Mono<ProductDto> result = productService.insert(kafkaDto);

        // result
        result.subscribe(System.out::println);
        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(dto)
                .verifyComplete();

    }
}