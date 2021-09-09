package com.virspit.virspitauth.domain.member.controller;

import com.virspit.virspitauth.domain.member.dto.request.MemberSignInRequestDto;
import com.virspit.virspitauth.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.domain.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    public ResponseEntity<String> signup(@RequestBody MemberSignUpRequestDto memberSignUpRequestDto){
        log.info("################## 회원가입 controller Start ###########");
        memberService.signUp(memberSignUpRequestDto);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/signin")
    public ResponseEntity<MemberSignInRequestDto> signin(@ApiParam("Signin User")
                                                        @RequestBody MemberSignInRequestDto memberSignInRequestDto){
        log.info("################## 로그인 controller Start ###########");
        return ResponseEntity.ok(memberService.singIn(memberSignInRequestDto));
    }
}
