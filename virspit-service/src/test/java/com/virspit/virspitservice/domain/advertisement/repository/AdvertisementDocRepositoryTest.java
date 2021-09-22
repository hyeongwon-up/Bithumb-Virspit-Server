package com.virspit.virspitservice.domain.advertisement.repository;

import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.entity.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class AdvertisementDocRepositoryTest {

    @Autowired
    private ReactiveMongoTemplate template;

    @Autowired
    private AdvertisementDocRepository repository;

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

        // when, assert
        StepVerifier.create(repository.findById(saved.getId()))
                .expectNext(saved)
                .verifyComplete();
    }

}