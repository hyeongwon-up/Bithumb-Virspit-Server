package com.virspit.virspitproduct.domain.sports.dto.response;

import com.virspit.virspitproduct.domain.sports.entity.Sports;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SportsResponseDto {
    @ApiModelProperty("종목 ID")
    private final Long id;

    @ApiModelProperty("종목 이름")
    private final String name;

    @ApiModelProperty("아이콘 이미지 주소")
    private final String iconUrl;

    private SportsResponseDto(Sports sports) {
        id = sports.getId();
        name = sports.getName();
        iconUrl = sports.getIconUrl();
    }

    public static SportsResponseDto of(Sports sports) {
        return new SportsResponseDto(sports);
    }

    public static List<SportsResponseDto> of(List<Sports> sportsList) {
        return sportsList.stream().map(SportsResponseDto::new).collect(Collectors.toList());
    }
}
