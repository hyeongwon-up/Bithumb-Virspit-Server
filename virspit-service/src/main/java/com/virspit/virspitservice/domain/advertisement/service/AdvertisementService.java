package com.virspit.virspitservice.domain.advertisement.service;

import com.virspit.virspitservice.domain.advertisement.dto.AdvertisementDto;
import com.virspit.virspitservice.domain.advertisement.entity.AdvertisementDoc;
import com.virspit.virspitservice.domain.advertisement.repository.AdvertisementDocRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final AdvertisementDocRepository advertisementDocRepository;

    @Transactional
    public Mono<AdvertisementDto> insert(AdvertisementDto productDto) {
        log.info("mongo db insert :{}",productDto);
        Mono<AdvertisementDto> result =  advertisementDocRepository.save(AdvertisementDoc.dtoToEntity(productDto))
                .map(AdvertisementDto::entityToDto);
        result.subscribe(p->log.info("result {}",p));
        return result;
    }

}
