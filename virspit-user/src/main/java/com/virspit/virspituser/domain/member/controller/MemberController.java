package com.virspit.virspituser.domain.member.controller;

import com.virspit.virspituser.domain.member.dto.request.MemberChangePwdRequestDto;
import com.virspit.virspituser.domain.member.dto.request.MemberEditInfoRequestDto;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.service.MemberService;
import com.virspit.virspituser.global.common.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @ApiOperation("memberId로 member 찾기")
    @GetMapping("/{id}")
    @Deprecated
    public Member findById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @ApiOperation("회원 정보 수정 (이름, 성별, 생년월일, 핸드폰번호)")
    @PutMapping("/info/{id}")
    public SuccessResponse<String> changeMemberInfo(@PathVariable("id") Long memberId,
                                                   @RequestBody MemberEditInfoRequestDto memberEditInfoRequestDto) {
        return SuccessResponse.of(memberService.changeMemberInfo(memberId, memberEditInfoRequestDto));
    }

    @ApiOperation("회원 비밀번호 변경")
    @PutMapping("/pwd")
    public SuccessResponse<String> changePwd(@RequestBody MemberChangePwdRequestDto memberChangePwdRequestDto) {
        return SuccessResponse.of(memberService.changePwd(memberChangePwdRequestDto));
    }

}
