package com.virspit.virspituser.domain.member.controller;

import com.virspit.virspituser.domain.member.dto.request.InitPwdRequestDto;
import com.virspit.virspituser.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspituser.domain.member.dto.response.MemberInfoResponseDto;
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

    @ApiOperation("feign = MemberId 로 사용자 정보 조회")
    @GetMapping("/{id}")
    public MemberInfoResponseDto findById(@PathVariable Long id) {
        return memberService.MemberInfoFindById(id);
    }


    @ApiOperation("feign - 회원가입 요청한 Member를 db에 저장")
    @PostMapping("/save")
    public MemberInfoResponseDto save(@RequestBody MemberSignUpRequestDto memberSignUpRequestDto) throws ApiException {
        return memberService.registry(memberSignUpRequestDto);
    }

    @ApiOperation("feign - email을 통해 member 정보 전달")
    @GetMapping
    public Member findByEmail(@RequestParam String memberEmail) {
        log.info("find email controller start");
        return memberService.findByEmail(memberEmail);
    }

    @ApiOperation("feign - email 중복 검사")
    @GetMapping("/check")
    public boolean checkByEmail(@RequestParam String memberEmail) {
        return memberService.checkByEmail(memberEmail);
    }

    @ApiOperation("feign - Member 비밀번호 변경 저장")
    @PostMapping("/pwd")
    public Boolean initPwd(@RequestBody InitPwdRequestDto initPwdRequestDto) {
        return memberService.initPwd(initPwdRequestDto);
    }


}
