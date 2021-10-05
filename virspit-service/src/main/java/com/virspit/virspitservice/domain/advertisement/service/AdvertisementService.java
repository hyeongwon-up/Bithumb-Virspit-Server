package com.virspit.virspitservice.domain.advertisement.service;

import com.virspit.virspitservice.domain.advertisement.common.WebfluxPagingResponseDto;
import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementUpdateRequestDto;
import com.virspit.virspitservice.domain.advertisement.dto.response.AdvertisementResponseDto;
import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementRequestDto;
import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.advertisement.repository.AdvertisementDocRepository;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import com.virspit.virspitservice.response.error.ErrorCode;
import com.virspit.virspitservice.response.error.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final ProductDocRepository productDocRepository;
    private final AdvertisementDocRepository advertisementDocRepository;

    private Mono<ProductDoc> findProductById(String productId) {
        return productDocRepository.findById(productId)
                .switchIfEmpty(
                        Mono.error(new GlobalException(String.format("id: {%s} 에 해당하는 product 가 없습니다.", productId), ErrorCode.ENTITY_NOT_FOUND)));
    }

    @Transactional
    public Mono<AdvertisementResponseDto> insert(AdvertisementRequestDto requestDto) {
        Mono<AdvertisementDoc> advertisementDoc = findProductById(requestDto.getProductId())
                .log()
                .flatMap(productDoc -> advertisementDocRepository.save(AdvertisementDoc.dtoToEntity(requestDto, productDoc)));
        return advertisementDoc.log()
                .map(AdvertisementResponseDto::entityToDto);
    }

    @Transactional(readOnly = true)
    public WebfluxPagingResponseDto getAll(Pageable pageable) {
        advertisementDocRepository
                .findAll(pageable)
                .map(AdvertisementResponseDto::entityToDto)
                .subscribe(s-> System.out.println(s));

        return WebfluxPagingResponseDto.of(
                advertisementDocRepository.count(),
                advertisementDocRepository
                        .findAll(pageable)
                        .log()
                        .map(AdvertisementResponseDto::entityToDto));
    }

    @Transactional(readOnly = true)
    public Mono<AdvertisementResponseDto> get(String id) {
        return advertisementDocRepository.findById(id)
                .map(AdvertisementResponseDto::entityToDto);
    }

    @Transactional
    public Mono<AdvertisementResponseDto> update(AdvertisementUpdateRequestDto requestDto, String id) {
        return advertisementDocRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new GlobalException(String.format("id: {%s} 에 해당하는 광고가 없습니다.", id), ErrorCode.ENTITY_NOT_FOUND)))
                .log()
                .flatMap(ad -> {
                    ad.update(requestDto);
                    return advertisementDocRepository.save(ad)
                            .map(AdvertisementResponseDto::entityToDto);
                });
    }

    @Transactional
    public Mono<Void> delete(String id) {
        return advertisementDocRepository.deleteById(id);
    }
}
