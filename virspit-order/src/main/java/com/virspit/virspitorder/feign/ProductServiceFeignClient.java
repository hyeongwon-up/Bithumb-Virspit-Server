package com.virspit.virspitorder.feign;


import com.virspit.virspitorder.dto.response.ProductResponseDto;
import com.virspit.virspitorder.response.result.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "virspit-product", url = "http://3.35.69.49:8081")
public interface ProductServiceFeignClient {

    @GetMapping(value = "/products/{productId}", consumes = "application/json")
    SuccessResponse<ProductResponseDto> findByProductId(@PathVariable(name = "productId") Long productId);
}
