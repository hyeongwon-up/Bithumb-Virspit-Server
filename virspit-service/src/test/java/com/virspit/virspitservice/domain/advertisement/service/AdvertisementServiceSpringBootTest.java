package com.virspit.virspitservice.domain.advertisement.service;

import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementRequestDto;
import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementUpdateRequestDto;
import com.virspit.virspitservice.domain.advertisement.dto.response.AdvertisementResponseDto;
import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.advertisement.repository.AdvertisementDocRepository;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdvertisementServiceSpringBootTest {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AdvertisementDocRepository repository;

    @Autowired
    private ProductDocRepository productDocRepository;

    ProductDoc productDoc = ProductDoc.builder()
            .id("1")
            .remainedCount(3)
            .createdDateTime(LocalDateTime.now())
            .exhibition(false)
            .build();
    @BeforeEach
    void setUp() {
        productDocRepository.save(productDoc).subscribe();
    }

    @AfterEach
    void after() {
        // 데이터 모두 삭제
        productDocRepository.deleteAll().subscribe();
        productDocRepository.deleteAll().subscribe();
    }

    @DisplayName("광고 작성")
    @Test
    void insert() {
        AdvertisementRequestDto requestDto = AdvertisementRequestDto.builder()
                .productId("1")
                .description("advertisement")
                .build();
        Mono<AdvertisementResponseDto> result = advertisementService.insert(requestDto);

        StepVerifier.create(result)
                .assertNext(p -> {
                    assertThat(p.getProduct()).isEqualTo(productDoc);
                    assertThat(p.getDescription()).isNotEqualTo("advertisement");
                });
    }

    AdvertisementDoc generate(String id) {
        return AdvertisementDoc.builder()
                .createdDate(LocalDateTime.now())
                .product(productDoc)
                .id(id)
                .description("description" + id)
                .build();
    }

    @DisplayName("전체 광고 목록을 가져온다.")
    @Test
    void getAll() {
        for (int i = 0; i < 3; i++) {
            repository.save(generate(String.valueOf(i + 1))).subscribe();
        }
        Pageable pageable = PageRequest.of(1, 1, Sort.by("createdDate").descending());

        StepVerifier.create(advertisementService.getAll(pageable).getData())
                .expectNextCount(1)
                .verifyComplete();
    }

    @DisplayName("id로 광고 조회한다")
    @Test
    void get() {
        AdvertisementDoc saved = repository.save(generate("12345")).block();

        StepVerifier.create(advertisementService.get("12345"))
                .expectNext(AdvertisementResponseDto.entityToDto(saved))
                .verifyComplete();
    }

    @DisplayName("id로 광고 조회한다")
    @Test
    void update() {
        AdvertisementDoc saved = repository.save(generate("123")).block();
        AdvertisementUpdateRequestDto requestDto = AdvertisementUpdateRequestDto.builder().build();

        assertThat(advertisementService.update(requestDto,"123").block().getId()).isEqualTo(saved.getId());
    }

    @DisplayName("광고 삭제")
    @Test
    void delete() {
        AdvertisementDoc saved = repository.save(generate("123")).block();
        advertisementService.delete(saved.getId());

        StepVerifier.create(advertisementService.get("123"))
                .expectNextCount(0)
                .verifyComplete();
    }
}