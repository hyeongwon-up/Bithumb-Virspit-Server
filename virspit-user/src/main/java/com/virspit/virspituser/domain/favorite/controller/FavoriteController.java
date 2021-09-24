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

    @DeleteMapping("/{id}")
    @ApiOperation("상품 좋아요 취소하기")
    public ResponseEntity<?> deleteFavorite(@PathVariable(name = "id") Long memberId, @RequestParam Long productId) {
        return ResponseEntity.ok(favoriteService.deleteFavorite(memberId, productId));
    }

    @GetMapping("/{id}")
    @ApiOperation("해당 Id의 member 좋아요 누른 상품 목록 가져오기")
    public ResponseEntity<?> getAllFavoriteByMemberId(@PathVariable(name = "id") Long memberId) {
        return ResponseEntity.ok(favoriteService.getAllFavoriteByMemberId(memberId));
    }



}
