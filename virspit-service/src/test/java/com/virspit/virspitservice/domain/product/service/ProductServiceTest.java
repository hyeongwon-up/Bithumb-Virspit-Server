package com.virspit.virspitservice.domain.product.service;

import com.virspit.virspitservice.domain.product.dto.ProductDto;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    private ProductService productService;

    @MockBean
    private ProductDocRepository repository;

    @BeforeEach
    void setUp(){
        productService = new ProductService(repository);
    }

    @DisplayName("카프카에서 받은 데이터를 mongoDB에 저장한다.")
    @Test
    void insert() {
        // given
        ProductDto dto = ProductDto.builder()
                .id(null)
                .name(UUID.randomUUID().toString())
                .createdDate(LocalDateTime.now())
                .build();
        ProductDoc entity = repository.save(ProductDoc.dtoToEntity(dto)).block();
        // when
        Mono<ProductDto> result = productService.insert(Mono.just(dto));
        System.out.println(repository);
        result.subscribe((r)->{
            System.out.println(r);
        });

        // assert
//        StepVerifier.create(repository.findAll())
//                .verifyComplete();

//                .expectNextCount(1)
//                .verifyComplete();

//        StepVerifier.create(result)
//                .assertNext(productDto -> {
//                    assertThat(productDto.getName()).isEqualTo(dto.getName());
//                })
//                .verifyComplete();

    }

    @Test
    void findAllProducts() {
    }

    @Test
    void findProductsBy() {
    }
}