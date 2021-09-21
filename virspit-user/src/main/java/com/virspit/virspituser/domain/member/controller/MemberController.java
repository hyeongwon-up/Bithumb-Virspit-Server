package com.virspit.virspituser.domain.member.controller;

import com.virspit.virspituser.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.service.MemberService;
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

    @ApiOperation("회원가입 요청한 Member를 db에 저장")
    @PostMapping("/save")
    public String save(@RequestBody MemberSignUpRequestDto memberSignUpRequestDto) {
        return memberService.registry(memberSignUpRequestDto);
    }

    @ApiOperation("email을 통해 member 정보 전달")
    @GetMapping
    public Member findByEmail(@RequestParam String memberEmail) {
        log.info("find email controller start");
        return memberService.findByEmail(memberEmail);
    }

    @ApiOperation("Member 비밀번호 변경 저장")
    @PostMapping("/pwd")
    public String changPwd(@RequestBody Member member) {
        return memberService.changePwd(member);
    }


}
