package com.virspit.virspitservice.domain.advertisement.controller;

import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementRequestDto;
import com.virspit.virspitservice.domain.advertisement.dto.response.AdvertisementResponseDto;
import com.virspit.virspitservice.domain.advertisement.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/advertisements")
@RequiredArgsConstructor
@RestController
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    public Mono<AdvertisementResponseDto> insert(@RequestBody AdvertisementRequestDto requestDto) {
        return advertisementService.insert(requestDto);
    }

    @GetMapping
    public Flux<AdvertisementResponseDto> getAll(@RequestParam int page, @RequestParam int size) {
        return advertisementService.getAll(PageRequest.of(page - 1, size, Sort.by("createdDate").descending()));
    }

    @GetMapping("/{id}")
    public Mono<AdvertisementResponseDto> get(@PathVariable String id) {
        return advertisementService.get(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody AdvertisementRequestDto requestDto, @PathVariable String id) {
        advertisementService.update(requestDto, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return advertisementService.delete(id);
    }
}
