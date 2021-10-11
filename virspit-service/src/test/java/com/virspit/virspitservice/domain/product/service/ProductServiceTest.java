package com.virspit.virspitservice.domain.product.service;

import com.virspit.virspitservice.domain.product.dto.*;
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
import java.util.List;

@DisplayName("서비스 유닛 테스트 (mock)")
@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDocRepository repositoryMock;

    private ProductKafkaDto kafkaDto = ProductKafkaDto.builder()
            .title("title")
            .description("test")
            .id("1")
            .detailImageUrl("")
            .exhibition(false)
            .event(Event.UPDATE)
            .nftImageUrl("")
            .nftInfo(new NftInfo("", ""))
            .price(1)
            .sportsInfo(new SportsInfo(1l, "name"))
            .teamPlayerInfo(new TeamPlayerInfo(1l, "name","TEAM"))
            .remainedCount(4)
            .startDateTime(LocalDateTime.now())
            .updateDateTime(LocalDateTime.now())
            .createdDateTime(LocalDateTime.now())
            .build();
    private ProductDoc product = ProductDoc.kafkaToEntity(kafkaDto);
    private ProductDto dto = ProductDto.entityToDto(product);
    private ProductDoc product2 = ProductDoc.builder()
            .id("2")
            .build();

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

        BDDMockito.when(repositoryMock.count())
                .thenReturn(Mono.just(20l));

        BDDMockito.when(repositoryMock.findAllById(List.of("1","2")))
                .thenReturn(Flux.just(product, product2));
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
        StepVerifier.create(productService.getAllProducts(PageRequest.of(0, 4, Sort.by("createdDate").descending()),"TEAM",1l))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @DisplayName("좋아요한 목록 Id 프로덕트 정보를 가져온다.")
    @Test
    void getFavorites(){
        StepVerifier.create(productService.getFavorites(List.of("1","2")))
                .expectSubscription()
                .expectNextCount(2)
                .verifyComplete();
    }
}