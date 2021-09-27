package com.virspit.virspitproduct.domain.product.controller;

import com.virspit.virspitproduct.domain.common.SuccessResponse;
import com.virspit.virspitproduct.domain.product.dto.request.ProductStoreRequestDto;
import com.virspit.virspitproduct.domain.product.dto.response.ProductResponseDto;
import com.virspit.virspitproduct.domain.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api("상품 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @ApiOperation(value = "전체 상품 목록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "검색어", paramType = "query"),
            @ApiImplicitParam(name = "teamPlayerId", value = "팀/플레이어 ID", paramType = "query"),
            @ApiImplicitParam(name = "sportsId", value = "종목 ID", paramType = "query")
    })
    @GetMapping
    public SuccessResponse<?> getProducts(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "teamPlayerId", required = false) Long teamPlayerId,
            @RequestParam(value = "sportsId", required = false) Long sportsId) {
        return SuccessResponse.of(productService.getProducts(keyword, teamPlayerId, sportsId, pageable));
    }

    @ApiOperation("상품 ID에 해당하는 상품 조회")
    @GetMapping("/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @ApiOperation("상품 등록")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(@ModelAttribute ProductStoreRequestDto productStoreRequestDto){
        return productService.createProduct(productStoreRequestDto);
    }

    @ApiOperation("상품 수정")
    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponseDto updateProduct(@PathVariable Long productId, @ModelAttribute ProductStoreRequestDto productStoreRequestDto) {
        return productService.updateProduct(productId, productStoreRequestDto);
    }
}
