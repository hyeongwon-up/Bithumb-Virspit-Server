package com.virspit.virspitservice.domain.product.controller;

import com.virspit.virspitservice.domain.advertisement.common.PageSupport;
import com.virspit.virspitservice.domain.product.dto.ProductDto;
import com.virspit.virspitservice.domain.product.dto.ProductKafkaDto;
import com.virspit.virspitservice.domain.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping("/products/list")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @ApiOperation("전체 상품 조회")
    @GetMapping
    public Mono<PageSupport> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return productService.getAllProducts(PageRequest.of(page - 1, size, Sort.by("createdDate").descending()));
    }

    @ApiOperation("상품 이름 검색")
    @GetMapping("/search")
    public Flux<ProductDto> searchProducts(@RequestParam("word") String word) {
        return productService.getProductsBy(word);
    }


    @ApiOperation("좋아요 상품 목록")
    @GetMapping("/favorite")
    public Flux<ProductDto> getFavorites(@RequestParam("ids") List<String> ids) {
        return productService.getFavorites(ids);
    }

    @ApiOperation("product 서버와 맞춤용 controller")
    @PostMapping("/add")
    public Mono<ProductDto> insertProduct(@RequestBody ProductKafkaDto productDto) {
        return productService.insert(productDto);
    }
}
