package com.virspit.virspitauth.feign;

import com.virspit.virspitauth.dto.request.InitPwdRequestDto;
import com.virspit.virspitauth.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.dto.model.Member;
import com.virspit.virspitauth.dto.response.MemberSignUpResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("virspit-user")
public interface MemberServiceFeignClient {

    @PostMapping(value = "/member/save", consumes = "application/json")
    MemberSignUpResponseDto save(@RequestBody MemberSignUpRequestDto memberSignUpRequestDto);

    @GetMapping(value = "/member", consumes = "application/json")
    Member findByEmail(@RequestParam String memberEmail);

    @PostMapping(value = "/member/pwd", consumes = "application/json")
    Boolean initPwd(@RequestBody InitPwdRequestDto initPwdRequestDto);

    @GetMapping(value = "/member/{id}", consumes = "application/json")
    Member findById(@PathVariable Long id);

}
