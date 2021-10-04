package com.virspit.virspitorder.feign;

import com.virspit.virspitorder.dto.response.ProductResponseDto;
import com.virspit.virspitorder.response.result.SuccessResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceFeignClientTest {

    @Autowired
    private ProductServiceFeignClient client;

    @DisplayName("productId로 product 정보 받기")
    @Test
    void findByProductId(){
        SuccessResponse<ProductResponseDto> result = client.findByProductId(1l);

        ProductResponseDto product = result.getData();
        assertThat(product.getId()).isEqualTo(1l);
    }
}