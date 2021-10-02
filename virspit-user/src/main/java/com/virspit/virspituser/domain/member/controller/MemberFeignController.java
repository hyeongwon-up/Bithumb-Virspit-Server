package com.virspit.virspituser.domain.member.controller;

import com.virspit.virspituser.domain.member.dto.request.InitPwdRequestDto;
import com.virspit.virspituser.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspituser.domain.member.dto.response.MemberSignUpResponseDto;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberFeignController {

    private final MemberService memberService;


    @ApiOperation("feign - 회원가입 요청한 Member를 db에 저장")
    @PostMapping("/save")
    public MemberSignUpResponseDto save(@RequestBody MemberSignUpRequestDto memberSignUpRequestDto) throws ApiException {
        return memberService.registry(memberSignUpRequestDto);
    }

    @ApiOperation("feign - email을 통해 member 정보 전달")
    @GetMapping
    public Member findByEmail(@RequestParam String memberEmail) {
        log.info("find email controller start");
        return memberService.findByEmail(memberEmail);
    }

    @ApiOperation("feign - Member 비밀번호 초기화 저장")
    @PostMapping("/pwd")
    public Boolean initPwd(@RequestBody InitPwdRequestDto initPwdRequestDto) {
        return memberService.initPwd(initPwdRequestDto);
    }


}
