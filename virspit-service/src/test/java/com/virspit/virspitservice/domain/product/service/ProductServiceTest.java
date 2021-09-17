package com.virspit.virspitservice.domain.product.service;

import com.virspit.virspitservice.domain.product.dto.ProductDto;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.entity.Type;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDocRepository repositoryMock;

    private ProductDoc product = ProductDoc.builder()
            .name(UUID.randomUUID().toString())
            .createdDate(LocalDateTime.now())
            .build();
    private ProductDto dto = ProductDto.entityToDto(product);

    @BeforeEach
    void setUp() {
        BDDMockito.when(repositoryMock.findAll())
                .thenReturn(Flux.just(product));

        BDDMockito.when(repositoryMock.save(product))
                .thenReturn(Mono.just(product));

        BDDMockito.when(repositoryMock.findByNameLikeOrderByCreatedDateDesc("product"))
                .thenReturn(Flux.just(product));
    }

    @DisplayName("카프카에서 받은 데이터를 mongoDB에 저장한다.")
    @Test
    void insert() {
        Mono<ProductDto> result = productService.insert(dto);

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @DisplayName("전체 상품 목록을 가져온다.")
    @Test
    void getAllProducts() {
        StepVerifier.create(productService.getAllProducts())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @DisplayName("전체 상품 목록 이름을 조회한다.")
    @Test
    void getProductsBy() {
        StepVerifier.create(productService.getProductsBy("product"))
                .expectSubscription()
                .expectNext(dto)
                .verifyComplete();
    }


}