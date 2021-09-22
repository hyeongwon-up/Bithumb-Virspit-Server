package com.virspit.virspitservice.domain.advertisement.service;

import com.virspit.virspitservice.domain.advertisement.dto.response.AdvertisementResponseDto;
import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementRequestDto;
import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.advertisement.repository.AdvertisementDocRepository;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final ProductDocRepository productDocRepository;
    private final AdvertisementDocRepository advertisementDocRepository;

    @Transactional
    public Mono<AdvertisementResponseDto> insert(AdvertisementRequestDto requestDto) {
        ProductDoc productDoc = productDocRepository.findById(requestDto.getProductId())
                .onErrorReturn(null)
                .block();

        return advertisementDocRepository.save(AdvertisementDoc.dtoToEntity(requestDto, productDoc))
                .map(AdvertisementResponseDto::entityToDto);
    }

    @Transactional(readOnly = true)
    public Flux<AdvertisementResponseDto> getAll(Pageable pageable) {
        return advertisementDocRepository.findAll()
                .map(AdvertisementResponseDto::entityToDto);
    }
}
