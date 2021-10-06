package com.virspit.virspitproduct.domain.product.controller;

import com.virspit.virspitproduct.domain.common.PagingResponseDto;
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

import java.io.IOException;

@Slf4j
@Api("상품 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @ApiOperation(value = "전체 상품 목록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "상품 제목", paramType = "query"),
            @ApiImplicitParam(name = "teamPlayerId", value = "팀/플레이어 ID", paramType = "query"),
            @ApiImplicitParam(name = "sportsId", value = "종목 ID", paramType = "query"),
            @ApiImplicitParam(name = "isTeam", value = "팀/플레이어 여부", paramType = "query")
    })
    @GetMapping
    public SuccessResponse<PagingResponseDto<ProductResponseDto>> getProducts(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) final Pageable pageable,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "teamPlayerId", required = false) Long teamPlayerId,
            @RequestParam(value = "sportsId", required = false) Long sportsId,
            @RequestParam(value = "isTeam", required = false) Boolean isTeam) {
        return SuccessResponse.of(productService.getProducts(title, teamPlayerId, sportsId, isTeam, pageable));
    }

    @ApiOperation("상품 ID에 해당하는 상품 조회")
    @GetMapping("/{productId}")
    public SuccessResponse<ProductResponseDto> getProduct(@PathVariable Long productId) {
        return SuccessResponse.of(productService.getProduct(productId));
    }

    @ApiOperation("상품 등록")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<ProductResponseDto> createProduct(@ModelAttribute ProductStoreRequestDto productStoreRequestDto) throws IOException {
        return SuccessResponse.of(productService.createProduct(productStoreRequestDto), HttpStatus.CREATED);
    }

    @ApiOperation("상품 수정")
    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<ProductResponseDto> updateProduct(@PathVariable Long productId, @ModelAttribute ProductStoreRequestDto productStoreRequestDto) throws IOException {
        return SuccessResponse.of(productService.updateProduct(productId, productStoreRequestDto), SuccessResponse.UPDATED_MESSAGE);
    }

    @ApiOperation(("상품 제거"))
    @DeleteMapping(value = "/{productId}")
    public SuccessResponse<ProductResponseDto> deleteProduct(@PathVariable Long productId) {
        return SuccessResponse.of(productService.deleteProduct(productId), SuccessResponse.DELETED_MESSAGE);
    }
}
