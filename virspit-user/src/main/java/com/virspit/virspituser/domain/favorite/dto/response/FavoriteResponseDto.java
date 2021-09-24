package com.virspit.virspituser.domain.favorite.dto.response;

import com.virspit.virspituser.domain.favorite.entity.Favorite;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FavoriteResponseDto {
    private Long productId;

    private FavoriteResponseDto(Favorite favorite) {
       productId = favorite.getProductId();
    }

    public static List<FavoriteResponseDto> of(List<Favorite> favoriteList) {
        return favoriteList.stream().map(FavoriteResponseDto::new).collect(Collectors.toList());
    }
}
