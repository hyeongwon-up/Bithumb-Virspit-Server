package com.virspit.virspitservice.domain.product.repository;

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

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


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

    ProductDoc generateDocument() {
        return template.insert(
                ProductDoc.builder()
                        .id(null)
                        .type(Type.PLAYER)
                        .name(UUID.randomUUID().toString())
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
    void insert(){
        // given
        ProductDoc product = ProductDoc.builder()
                .id(null)
                .type(Type.PLAYER)
                .name(UUID.randomUUID().toString())
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
}