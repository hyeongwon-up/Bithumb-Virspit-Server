package com.virspit.virspitauth.feign;

import com.virspit.virspitauth.dto.request.InitPwdRequestDto;
import com.virspit.virspitauth.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.dto.model.Member;
import com.virspit.virspitauth.dto.response.MemberInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//@FeignClient(name = "VIRSPIT-USER", url = "http://3.38.44.130:8081")
@FeignClient(name = "virspit-user")
public interface MemberServiceFeignClient {

    @PostMapping(value = "/member/save", consumes = "application/json")
    MemberInfoResponseDto save(@RequestBody MemberSignUpRequestDto memberSignUpRequestDto);

    @GetMapping(value = "/member", consumes = "application/json")
    Member findByEmail(@RequestParam("memberEmail") String memberEmail);

    @GetMapping(value = "/member/check", consumes = "application/json")
    Boolean checkByEmail(@RequestParam("memberEmail") String memberEmail);

    @PostMapping(value = "/member/pwd", consumes = "application/json")
    Boolean initPwd(@RequestBody InitPwdRequestDto initPwdRequestDto);

    @GetMapping(value = "/member/{id}", consumes = "application/json")
    Member findById(@PathVariable(name = "id") Long id);

    @GetMapping(value = "/member/feign", consumes = "application/json")
    String check();


}
