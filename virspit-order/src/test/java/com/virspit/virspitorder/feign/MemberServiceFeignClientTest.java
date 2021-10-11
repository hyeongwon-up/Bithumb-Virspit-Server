package com.virspit.virspitorder.feign;

import com.virspit.virspitorder.dto.response.MemberResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceFeignClientTest {
    @Autowired
    private MemberServiceFeignClient client;

    @DisplayName("memberID로 wallet 정보 받기")
    @Test
    void findWalletByMemberId(){
        String result = client.findWalletByMemberId(2l);
        System.out.println(result);
    }

    @DisplayName("memberID로 member 정보 받기")
    @Test
    void findByMemberId(){
        MemberResponseDto result = client.findByMemberId(2l);
        System.out.println(result);
    }
}