package com.virspit.virspitservice.domain.advertisement.service;

import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementRequestDto;
import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementUpdateRequestDto;
import com.virspit.virspitservice.domain.advertisement.dto.response.AdvertisementResponseDto;
import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.advertisement.repository.AdvertisementDocRepository;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("서비스 유닛 테스트 (mock)")
@ExtendWith(SpringExtension.class)
class AdvertisementServiceTest {

    @InjectMocks
    private AdvertisementService advertisementService;

    @Mock
    private AdvertisementDocRepository repository;

    @Mock
    private ProductDocRepository productDocRepository;

    private ProductDoc product = ProductDoc.builder()
            .id("1")
            .createdDateTime(LocalDateTime.now())
            .build();

    private AdvertisementDoc advertisement = AdvertisementDoc.builder()
            .product(product)
            .build();

    private AdvertisementRequestDto requestDto = AdvertisementRequestDto.builder()
            .productId("1")
            .description("request")
            .build();

    private AdvertisementUpdateRequestDto updateRequestDto = AdvertisementUpdateRequestDto.builder()
            .description("update")
            .build();

    @BeforeEach
    void setUp() {
        when(repository.count()).thenReturn(Mono.just(20l));

        when(productDocRepository.findById(requestDto.getProductId()))
                .thenReturn(Mono.just(product));

        when(repository.save(advertisement))
                .thenReturn(Mono.just(advertisement));

        when(repository.findAll(PageRequest.of(0, 4, Sort.by("createdDate").descending())))
                .thenReturn(Flux.just(advertisement));

        when(repository.findById("1"))
                .thenReturn(Mono.just(advertisement));

        when(productDocRepository.findById("1"))
                .thenReturn(Mono.just(product));

        when(productDocRepository.deleteById("1"))
                .thenReturn(Mono.empty());
    }

    @DisplayName("광고 생성")
    @Test
    void insert() {
        Mono<AdvertisementResponseDto> result = advertisementService.insert(requestDto);

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @DisplayName("광고 목록")
    @Test
    void getAll() {
        StepVerifier.create(advertisementService.getAll(
                PageRequest.of(0, 4, Sort.by("createdDate").descending())))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @DisplayName("광고 id 로 가져오기")
    @Test
    void get() {
        StepVerifier.create(advertisementService.get("1"))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @DisplayName("광고 수정")
    @Test
    void update() {
        assertThat(advertisementService.update(updateRequestDto, "1"))
                .isEqualTo(AdvertisementResponseDto.entityToDto(advertisement));

    }

    @DisplayName("광고 삭제")
    @Test
    void delete() {
        when(advertisementService.delete("1")).thenReturn(Mono.empty());
    }
}