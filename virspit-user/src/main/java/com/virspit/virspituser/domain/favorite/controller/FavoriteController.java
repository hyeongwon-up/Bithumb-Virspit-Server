package com.virspit.virspituser.domain.favorite.controller;

import com.virspit.virspituser.domain.favorite.service.FavoriteService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{id}")
    @ApiOperation("상품 좋아요하기")
    public ResponseEntity<?> createFavorite(@PathVariable(name = "id") Long memberId, @RequestParam Long productId) {
        return ResponseEntity.ok(favoriteService.createFavorite(memberId, productId));
    }


}
