package com.virspit.virspitauth.feign;

import com.virspit.virspitauth.dto.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


class MemberServiceFeignClientTest {

    private MemberServiceFeignClient memberServiceFeignClient;

    @Test
    void save() {
    }

    @Test
    void findByEmail() {
        Member member = memberServiceFeignClient.findByEmail("string");
        System.out.println(member.toString());
    }

    @Test
    void edit() {
    }

    @Test
    void findById() {
    }
}