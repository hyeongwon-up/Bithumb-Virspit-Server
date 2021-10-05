package com.virspit.virspitorder.feign;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberServiceFeignClientTest {
    @Autowired
    private MemberServiceFeignClient client;

    @DisplayName("productId로 product 정보 받기")
    @Test
    void findByProductId(){
        String result = client.findByMemberId(2l);
        System.out.println(result);
    }
}