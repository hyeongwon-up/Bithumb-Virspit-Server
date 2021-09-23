package com.virspit.virspitservice.domain.advertisement.service;

import com.virspit.virspitservice.domain.advertisement.dto.response.AdvertisementResponseDto;
import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementRequestDto;
import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.advertisement.repository.AdvertisementDocRepository;
import com.virspit.virspitservice.domain.product.entity.ProductDoc;
import com.virspit.virspitservice.domain.product.repository.ProductDocRepository;
import com.virspit.virspitservice.domain.response.error.ErrorCode;
import com.virspit.virspitservice.domain.response.error.GlobalException;
import com.virspit.virspitservice.domain.response.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final ProductDocRepository productDocRepository;
    private final AdvertisementDocRepository advertisementDocRepository;

    @Transactional
    public Mono<AdvertisementResponseDto> insert(AdvertisementRequestDto requestDto) {
        Optional<ProductDoc> productDoc = productDocRepository.findById(requestDto.getProductId()).blockOptional();
        if (!productDoc.isPresent()) {
            throw new GlobalException(String.format("id: {%s} 에 해당하는 product 가 없습니다.", requestDto.getProductId()), ErrorCode.ENTITY_NOT_FOUND);
        }
        Mono<AdvertisementResponseDto> result = advertisementDocRepository.save(AdvertisementDoc.dtoToEntity(requestDto, productDoc.get()))
                .map(AdvertisementResponseDto::entityToDto).log();
        return result;
    }

    @Transactional(readOnly = true)
    public Flux<AdvertisementResponseDto> getAll(Pageable pageable) {
        return advertisementDocRepository.findAll(pageable)
                .map(AdvertisementResponseDto::entityToDto);
    }

    @Transactional(readOnly = true)
    public Mono<AdvertisementResponseDto> get(String id) {
        return advertisementDocRepository.findById(id)
                .map(AdvertisementResponseDto::entityToDto);
    }

    @Transactional
    public AdvertisementResponseDto update(AdvertisementRequestDto requestDto, String id) {
        Optional<AdvertisementDoc> advertisementDocOptional = advertisementDocRepository.findById(id).blockOptional();
        if (!advertisementDocOptional.isPresent()) {
            throw new GlobalException(String.format("id: {%s} 에 해당하는 advertise 가 없습니다.", id), ErrorCode.ENTITY_NOT_FOUND);

        }
        Optional<ProductDoc> productDoc = productDocRepository.findById(requestDto.getProductId()).blockOptional();
        if (!productDoc.isPresent()) {
            throw new GlobalException(String.format("id: {%s} 에 해당하는 product 가 없습니다.", requestDto.getProductId()), ErrorCode.ENTITY_NOT_FOUND);
        }
        AdvertisementDoc advertisement = advertisementDocOptional.get();
        advertisement.update(requestDto, productDoc.get());
        return AdvertisementResponseDto.entityToDto(advertisement);
    }

    @Transactional
    public Mono<Void> delete(String id) {
        return advertisementDocRepository.deleteById(id);
    }
}
