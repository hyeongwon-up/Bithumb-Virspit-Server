package com.virspit.virspitservice.domain.product.controller;

import com.virspit.virspitservice.domain.product.dto.ProductDto;
import com.virspit.virspitservice.domain.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    ProductService service;

    @Autowired
    private WebTestClient client;

    ProductDto generateDocument(String name) {
        return ProductDto.builder()
                .id(name)
                .createdDate(LocalDateTime.now())
                .build();
    }


    @DisplayName("전체 상품 가져오기")
    @Test
    void allProducts() {
        // given
        ProductDto dto = ProductDto.builder()
                .name(UUID.randomUUID().toString())
                .count(5)
                .createdDate(LocalDateTime.now())
                .build();
        Flux<ProductDto> productMono = Flux.just(dto);

        // when
        when(service.getAllProducts()).thenReturn(productMono);

        // assert
        client.get()
                .uri("/products/list")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("검색 상품 가져오기")
    @Test
    void searchProducts() {
        // given
        List<ProductDto> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(generateDocument("product" + i));
        }

        // when
        when(service.getProductsBy("product")).thenReturn(Flux.fromIterable(list));

        // assert
        client.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/products/list/search")
                                .queryParam("word", "product")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }
}