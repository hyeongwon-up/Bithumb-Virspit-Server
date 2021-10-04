package com.virspit.virspitservice.domain.product.repository;

import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class ProductDocRepositoryTest {

    @Autowired
    private ReactiveMongoTemplate template;

    @Autowired
    private ProductDocRepository repository;

    @BeforeEach
    void setUp() {
        // 데이터 모두 삭제
        template.dropCollection(ProductDoc.class).subscribe();
    }

    ProductDoc generateDocument(String name) {
        return template.insert(
                ProductDoc.builder()
                        .id(null)
                        .title(name)
                        .createdDateTime(LocalDateTime.now())
                        .remainedCount(4)
                        .price(10000)
                        .exhibition(false)
                        .description("description")
                        .build()
        ).block();
    }

    @DisplayName("product 이름으로 찾을 때 - 저장한 데이터와 이름으로 조회한 데이터가 같다.")
    @Test
    void findByName() {
        // given
        ProductDoc saved = generateDocument("product01");

        // when, assert
        StepVerifier.create(repository.findByTitle(saved.getTitle()))
                .expectNext(saved)
                .verifyComplete();
    }

    @DisplayName("전체 결과를 페이징 처리해서 (최신순으로) 가져온다.")
    @Test
    void findAllPagingBy() {
        // given
        for (int i = 0; i < 10; i++) {
            generateDocument("product" + (i + 1));
        }

        // when, assert
        Flux<ProductDoc> result = repository.findAll(PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdDate")));
        AtomicInteger i = new AtomicInteger();
        result.subscribe(r->{
            i.getAndIncrement();
            System.out.println(r+ " i-> "+i);
        });

        StepVerifier
                .create(result)
                .expectNextCount(3)
                .verifyComplete();
    }

    @DisplayName("특정 글자를 포함하는 리스트를 반환한다.")
    @Test
    void findByNameLike() {
        // given
        for (int i = 0; i < 10; i++) {
            generateDocument("product" + (i + 1));
        }
        Flux<ProductDoc> result = repository.findByTitleLikeOrderByCreatedDateDesc("product");
        AtomicInteger i = new AtomicInteger();
        result.subscribe(r->{
            i.getAndIncrement();
            System.out.println(r+ " i-> "+i);
        });

        // when, assert
        StepVerifier
                .create(repository.findByTitleLikeOrderByCreatedDateDesc("product"))
                .expectNextCount(10)
                .verifyComplete();
    }

    @DisplayName("상품 이름에 특정 글자를 포함하는 리스트를 반환한다. -pageable")
    @Test
    void findByNameLikePagingBy() {
        // given
        for (int i = 0; i < 10; i++) {
            generateDocument("product" + (i + 1));
        }

        Flux<ProductDoc> result = repository.findByTitleLikePagingBy("1", PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "createdDate")));

        AtomicInteger i = new AtomicInteger();
        result.subscribe(r->{
            i.getAndIncrement();
            System.out.println(r+ " i-> "+i);
        });

        // when, assert
        StepVerifier
                .create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @DisplayName("min, max 금액 사이의 상품을 조회 한다.")
    @Test
    void findByPriceBetween() {
        // given
        for (int i = 0; i < 10; i++) {
            generateDocument("product" + (i + 1));
        }

        Flux<ProductDoc> result = repository.findByPriceBetween(Range.closed(0, 10001));

        AtomicInteger i = new AtomicInteger();
        result.subscribe(r->{
            i.getAndIncrement();
            System.out.println(r+ " i-> "+i);
        });

        // when, assert
        StepVerifier
                .create(result)
                .expectNextCount(10)
                .verifyComplete();
    }
}