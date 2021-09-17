package com.virspit.virspitproduct.domain.sports.dto.response;

import com.virspit.virspitproduct.domain.sports.entity.Sports;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SportsResponseDto {
    private Long id;
    private String name;
    private String iconUrl;

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
