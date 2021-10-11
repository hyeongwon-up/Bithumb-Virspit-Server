package com.virspit.virspitorder.feign;

import com.virspit.virspitorder.dto.response.MemberResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "virspit-user", url = "http://3.38.42.161:8081")
public interface MemberServiceFeignClient {

    @GetMapping(value = "/wallet/{id}", consumes = "application/json")
    String findWalletByMemberId(@PathVariable(name = "id") Long memberId);

    @GetMapping(value = "/member/{id}", consumes = "application/json")
    MemberResponseDto findByMemberId(@PathVariable(name = "id") Long memberId);
}
