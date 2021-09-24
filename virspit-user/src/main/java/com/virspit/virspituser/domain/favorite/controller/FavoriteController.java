package com.virspit.virspituser.domain.favorite.controller;

import com.virspit.virspituser.domain.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    
}
