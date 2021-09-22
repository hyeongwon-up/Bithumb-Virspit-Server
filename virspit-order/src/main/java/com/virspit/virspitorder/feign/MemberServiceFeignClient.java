package com.virspit.virspitorder.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("virspit-user")
public interface MemberServiceFeignClient {

    @GetMapping(value = "/wallet/{id}", consumes = "application/json")
    String findByMemberId(@PathVariable(name = "id") Long memberId);

}
