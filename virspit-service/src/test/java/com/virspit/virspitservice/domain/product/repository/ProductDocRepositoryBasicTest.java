package com.virspit.virspitservice.domain.product.repository;

import com.virspit.virspitservice.domain.product.entity.ProductDoc;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataMongoTest
class ProductDocRepositoryBasicTest {

    @Autowired
    private ReactiveMongoTemplate template;

    @Autowired
    private ProductDocRepository repository;


    @BeforeEach
    void setUp() {
        // 데이터 모두 삭제
        template.dropCollection(ProductDoc.class).subscribe();
    }

    ProductDoc generateDocument() {
        return template.insert(
                ProductDoc.builder()
                        .id(null)
                        .title(UUID.randomUUID().toString())
                        .startDate(LocalDateTime.now())
                        .count(4)
                        .price(10000)
                        .exhibition(false)
                        .description("description")
                        .build()
        ).block();
    }

    @DisplayName("save 를 실행 후 저장된 데이터는 insert 한 데이터와 동일 데이터다.")
    @Test
    void insert() {
        // given
        ProductDoc product = ProductDoc.builder()
                .id(null)
                .title(UUID.randomUUID().toString())
                .startDate(LocalDateTime.now())
                .count(4)
                .price(10000)
                .exhibition(false)
                .description("description")
                .build();
        // when
        ProductDoc saved = repository.save(product).block();
        // assert
        assertThat(saved).isEqualTo(product);
    }

    @DisplayName("save 를 실행 후 저장된 데이터는 update 한 데이터와 동일 데이터다.")
    @Test
    void update() {
        // given
        ProductDoc saved = generateDocument();

        String name = UUID.randomUUID().toString();
        String id = saved.getId();
        ProductDoc updateDoc = ProductDoc.builder()
                .id(id)
                .title(name)
                .build();

        // when
        repository.save(updateDoc).block();

        // assert
        StepVerifier
                .create(template.findById(saved.getId(), ProductDoc.class))
                .assertNext(product -> {
                    assertThat(product.getId()).isEqualTo(id);
                    assertThat(product.getTitle()).isEqualTo(name);
                })
                .verifyComplete();
    }

    @DisplayName("전체 조회 했을 때 insert 한 갯수만큼 갯수가 조회된다.")
    @Test
    void findAll() {
        // given
        generateDocument();
        generateDocument();
        generateDocument();

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
        ProductDoc saved = generateDocument();

        // when, assert
        StepVerifier.create(repository.findById(saved.getId()))
                .expectNext(saved)
                .verifyComplete();
    }

    @DisplayName("단일 삭제 - 데이터가 삭제 되면 count 갯수가 줄어든다.")
    @Test
    void deleteById() {
        // when
        ProductDoc product = generateDocument();

        // given
        StepVerifier
                .create(repository.deleteById(product.getId()))
                .expectNextCount(0)
                .verifyComplete();

        // assert
        StepVerifier
                .create(repository.findById(product.getId()))
                .expectNextCount(0)
                .verifyComplete();
    }
}