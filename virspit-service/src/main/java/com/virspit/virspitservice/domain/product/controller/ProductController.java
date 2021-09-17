package com.virspit.virspitservice.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product/list")
@RequiredArgsConstructor
@RestController
public class ProductController {

    @GetMapping
    public ResponseEntity getList(){
        return ResponseEntity.ok().build();
    }

}
