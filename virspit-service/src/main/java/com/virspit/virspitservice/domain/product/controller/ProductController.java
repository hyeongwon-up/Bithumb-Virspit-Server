package com.virspit.virspitservice.domain.product.controller;

import com.virspit.virspitservice.domain.product.dto.ProductDto;
import com.virspit.virspitservice.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RequestMapping("/products/list")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<ProductDto> allProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public Flux<ProductDto> searchProducts(@RequestParam("word") String word) {
        return productService.getProductsBy(word);
    }

}
