package com.virspit.virspitservice.domain.advertisement.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ApiModel("페이징 응답 DTO")
@AllArgsConstructor
@Getter
public class WebfluxPagingResponseDto<T> {
    @ApiModelProperty("총 개수")
    private Mono<Long> totalCount;

    @ApiModelProperty("현재 페이지의 목록")
    private Flux<T> data;

    public static <T> WebfluxPagingResponseDto of(Mono<Long> totalCount, Flux<T> data) {
        return new WebfluxPagingResponseDto<>(totalCount, data);
    }
}
