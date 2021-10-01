package com.virspit.virspitservice.domain.product.service;

import com.virspit.virspitservice.domain.product.dto.ProductDto;
import com.virspit.virspitservice.domain.product.dto.ProductKafkaDto;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;


@DisplayName("서비스 유닛 테스트 (mock)")
@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDocRepository repositoryMock;

    private ProductDoc product = ProductDoc.builder()
            .title(UUID.randomUUID().toString())
            .createdDate(LocalDateTime.now())
            .build();
    private ProductKafkaDto kafkaDto = ProductKafkaDto.builder()
            .title(product.getTitle())
            .createdDate(product.getCreatedDate())
            .build();
    private ProductDto dto = ProductDto.entityToDto(product);

    @BeforeEach
    void setUp() {
        BDDMockito.when(repositoryMock.findAll())
                .thenReturn(Flux.just(product));

        BDDMockito.when(repositoryMock.save(product))
                .thenReturn(Mono.just(product));

        BDDMockito.when(repositoryMock.findByTitleLikeOrderByCreatedDateDesc("product"))
                .thenReturn(Flux.just(product));

        BDDMockito.when(repositoryMock.findAll(PageRequest.of(0, 4, Sort.by("createdDate").descending())))
                .thenReturn(Flux.just(product));

    }

    @DisplayName("카프카에서 받은 데이터를 mongoDB에 저장한다.")
    @Test
    void insert() {
        Mono<ProductDto> result = productService.insert(kafkaDto);

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

    @DisplayName("전체 상품 목록을 페이징 처리해서 가져온다.")
    @Test
    void getAllPaging() {
        StepVerifier.create(productService.getAllProducts(PageRequest.of(0, 4, Sort.by("createdDate").descending())))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

}