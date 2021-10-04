package com.virspit.virspitservice.domain.product.controller;

import com.virspit.virspitservice.domain.advertisement.common.WebfluxPagingResponseDto;
import com.virspit.virspitservice.domain.product.dto.ProductDto;
import com.virspit.virspitservice.domain.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RequestMapping("/products/list")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @ApiOperation("전체 상품 조회")
    @GetMapping
    public WebfluxPagingResponseDto getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return productService.getAllProducts(PageRequest.of(page - 1, size, Sort.by("createdDate").descending()));
    }

    @ApiOperation("상품 이름 검색")
    @GetMapping("/search")
    public Flux<ProductDto> searchProducts(@RequestParam("word") String word) {
        return productService.getProductsBy(word);
    }

}
