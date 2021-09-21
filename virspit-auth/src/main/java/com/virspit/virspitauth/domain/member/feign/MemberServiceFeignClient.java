package com.virspit.virspitauth.domain.member.feign;

import com.virspit.virspitauth.domain.member.dto.request.MemberSignUpRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("virspit-user")
public interface MemberServiceFeignClient {

    @PostMapping(value = "/member/save", consumes = "application/json")
    String save(@RequestBody MemberSignUpRequestDto memberSignUpRequestDto);

    @GetMapping(value = "/member", consumes = "application/json")
    Member findByEmail(@RequestParam String memberEmail);


}
