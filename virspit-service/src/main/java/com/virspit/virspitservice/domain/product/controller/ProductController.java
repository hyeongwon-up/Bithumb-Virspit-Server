package com.virspit.virspitservice.domain.product.controller;

import com.virspit.virspitservice.domain.product.dto.ProductResponseDto;
import com.virspit.virspitservice.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequestMapping("/products/list")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<ProductResponseDto> allProducts(){
        return productService.getAllProducts();
    }

}
