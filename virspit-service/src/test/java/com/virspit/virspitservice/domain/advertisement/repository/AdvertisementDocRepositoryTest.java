package com.virspit.virspitservice.domain.advertisement.repository;

import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.entity.Type;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class AdvertisementDocRepositoryTest {

    @Autowired
    private ReactiveMongoTemplate template;

    @Autowired
    private AdvertisementDocRepository repository;

    @Autowired
    private ProductDocRepository productDocRepository;

    @BeforeEach
    void setUp() {
        // 데이터 모두 삭제
        template.dropCollection(ProductDoc.class).subscribe();
        template.dropCollection(AdvertisementDoc.class).subscribe();
    }

    AdvertisementDoc generate(String name) {
        ProductDoc product = ProductDoc.builder()
                .id(null)
                .type(Type.PLAYER)
                .name(name)
                .createdDate(LocalDateTime.now())
                .count(4)
                .price(10000)
                .exhibition(false)
                .description("description")
                .build();
        template.insert(product);
        return template.insert(
                AdvertisementDoc.builder()
                        .id(null)
                        .product(product)
                        .createdDate(LocalDateTime.now())
                        .description("description~" + product.getName())
                        .build()).block();
    }

    @DisplayName("save 를 실행 후 저장된 데이터는 insert 한 데이터와 동일 데이터다.")
    @Test
    void insert() {
        AdvertisementDoc saved = generate("adv1");
        assertThat(saved.getProduct().getName()).isEqualTo("adv1");
    }

    @DisplayName("전체 조회 했을 때 insert 한 갯수만큼 갯수가 조회된다.")
    @Test
    void findAll() {
        // given
        generate("1");
        generate("2");
        generate("3");

        repository.findAll().subscribe(System.out::println);

        // when, assert
        StepVerifier
                .create(repository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @DisplayName("단일 조회 - 저장된 데이터와 조회한 데이터가 일치해야 한다.")
    @Test
    void findById() {
        // given
        AdvertisementDoc saved = generate("1");

        repository.findById(saved.getId()).subscribe(i-> System.out.println(i));

        // when, assert
        StepVerifier.create(repository.findById(saved.getId()))
                .expectNext(saved)
                .verifyComplete();
    }

    @DisplayName("product 저장후 advertisement 저장")
    @Test
    void insert2() {
        ProductDoc product = ProductDoc.builder()
                .id("id")
                .type(Type.PLAYER)
                .name(UUID.randomUUID().toString())
                .startDate(LocalDateTime.now())
                .count(4)
                .price(10000)
                .exhibition(false)
                .description("description")
                .build();
        ProductDoc saved = productDocRepository.save(product).block();

        ProductDoc productDoc = productDocRepository.findById("id").block();

        AdvertisementDoc advertisement = AdvertisementDoc.builder()
                .product(productDoc)
                .description("!!!")
                .build();

        AdvertisementDoc result = repository.save(advertisement).block();
        System.out.println(result);

        assertThat(result.getProduct()).isEqualTo(saved);
    }

    @DisplayName("광고 목록")
    @Test
    void findAll_pageable(){
        Pageable pageable = PageRequest.of(0, 3, Sort.by("createdDate").descending());
        // given
        for (int i = 0; i < 10; i++) {
            generate("advertisement" + (i + 1));
        }

        // when, assert
        Flux<AdvertisementDoc> result = repository.findAll(pageable);
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

    @DisplayName("광고 수정")
    @Test
    void update(){
        AdvertisementDoc saved = generate("prev");

        String id = saved.getId();
        LocalDateTime now = LocalDateTime.now();

        AdvertisementDoc updateDoc = AdvertisementDoc.builder()
                .id(id)
                .updatedDate(now)
                .build();

        // when
        AdvertisementDoc update = repository.save(updateDoc).block();

        // assert
        StepVerifier
                .create(template.findById(updateDoc.getId(), AdvertisementDoc.class))
                .expectNext(update)
                .verifyComplete();
    }

    @DisplayName("광고 삭제")
    @Test
    void delete(){
        // when
        AdvertisementDoc advertisement = generate("delete");

        // given
        StepVerifier
                .create(repository.deleteById(advertisement.getId()))
                .expectNextCount(0)
                .verifyComplete();

        // assert
        StepVerifier
                .create(repository.findById(advertisement.getId()))
                .expectNextCount(0)
                .verifyComplete();
    }
}