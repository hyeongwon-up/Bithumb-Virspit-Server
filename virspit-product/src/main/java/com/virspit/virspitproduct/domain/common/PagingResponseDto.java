package com.virspit.virspitproduct.domain.common;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@ApiModel("페이징 응답 DTO")
@AllArgsConstructor
@Getter
public class PagingResponseDto<T> {
    private Long totalCount;
    private List<T> list;
}
