package com.virspit.virspituser.domain.member.controller;

import com.virspit.virspituser.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
        return memberService.save(memberSignUpRequestDto);
    }

    @ApiOperation("email을 통해 member 정보 전달")
    @GetMapping
    public Member findByEmail(@RequestParam String memberEmail) {
        log.info("find email controller start");
        return memberService.findByEmail(memberEmail);
    }


}
