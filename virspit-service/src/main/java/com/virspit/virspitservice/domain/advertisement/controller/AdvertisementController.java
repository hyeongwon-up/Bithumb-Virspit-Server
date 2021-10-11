package com.virspit.virspitservice.domain.advertisement.controller;

import com.virspit.virspitservice.domain.common.PageSupport;
import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementRequestDto;
import com.virspit.virspitservice.domain.advertisement.dto.request.AdvertisementUpdateRequestDto;
import com.virspit.virspitservice.domain.advertisement.dto.response.AdvertisementResponseDto;
import com.virspit.virspitservice.domain.advertisement.service.AdvertisementService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/advertisements")
@RequiredArgsConstructor
@RestController
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @ApiOperation("광고 추가")
    @PostMapping
    public Mono<AdvertisementResponseDto> insert(@RequestBody AdvertisementRequestDto requestDto) {
        return advertisementService.insert(requestDto);
    }

    @ApiOperation("광고 전체 목록 페이징 조회")
    @GetMapping
    public Flux getAll(@RequestParam("page") int page, @RequestParam("size") int size) { ;
        return advertisementService.getAll(PageRequest.of(page - 1, size, Sort.by("createdDate").descending()));
    }

    @ApiOperation("광고 전체 목록 페이징 조회")
    @GetMapping("/page")
    public Mono<PageSupport> getAll2(@RequestParam("page") int page, @RequestParam("size") int size) {
        return advertisementService.getByPage(PageRequest.of(page - 1, size, Sort.by("createdDate").descending()));
    }

    @ApiOperation("광고 id로 조회")
    @GetMapping("/{id}")
    public Mono<AdvertisementResponseDto> get(@PathVariable String id) {
        return advertisementService.get(id);
    }

    @ApiOperation("광고 내용 업데이트")
    @PutMapping("/{id}")
    public Mono<AdvertisementResponseDto> update(@RequestBody AdvertisementUpdateRequestDto requestDto, @PathVariable String id) {
        return advertisementService.update(requestDto, id);
    }

    @ApiOperation("광고 삭제")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return advertisementService.delete(id);
    }
}
