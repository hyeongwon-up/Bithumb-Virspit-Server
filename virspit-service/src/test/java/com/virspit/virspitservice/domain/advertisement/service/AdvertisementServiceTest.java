package com.virspit.virspitservice.domain.advertisement.service;

import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.advertisement.repository.AdvertisementDocRepository;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@DisplayName("서비스 유닛 테스트 (mock)")
@ExtendWith(SpringExtension.class)
class AdvertisementServiceTest {
    @InjectMocks
    private AdvertisementService advertisementService;

    @Mock
    private AdvertisementDocRepository repository;

    private ProductDoc product = ProductDoc.builder()
            .name(UUID.randomUUID().toString())
            .createdDate(LocalDateTime.now())
            .build();
    private AdvertisementDoc advertisement = AdvertisementDoc.builder()
            .product(product)
            .build();

    @BeforeEach
    void setUp() {
        BDDMockito.when(repository.findAll())
                .thenReturn(Flux.just(advertisement));

        BDDMockito.when(repository.save(advertisement))
                .thenReturn(Mono.just(advertisement));

    }

    @DisplayName("광고 생성")
    @Test
    void insert(){

    }

    @DisplayName("광고 목록")
    @Test
    void getAll(){

    }

    @DisplayName("광고 id 로 가져오기")
    @Test
    void get(){

    }

    @DisplayName("광고 수정")
    @Test
    void update(){

    }

    @DisplayName("광고 삭제")
    @Test
    void delete(){

    }
}