package com.virspit.virspitauth.domain.member.controller;

import com.virspit.virspitauth.domain.member.dto.request.MemberSignInRequestDto;
import com.virspit.virspitauth.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.domain.member.dto.response.MemberSignInResponseDto;
import com.virspit.virspitauth.domain.member.entity.Member;
import com.virspit.virspitauth.domain.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody MemberSignUpRequestDto memberSignUpRequestDto) {
        return ResponseEntity.ok(memberService.register(memberSignUpRequestDto));
    }

    @PostMapping("/login")
    public MemberSignInResponseDto login(@RequestBody MemberSignInRequestDto memberSignInRequestDto) throws Exception {
        return memberService.login(memberSignInRequestDto);
    }

    @GetMapping(path="/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("useremail") String userEmail, @RequestParam("key") String hash) {
        log.info("verify controller 동작시작");
       return ResponseEntity.ok(memberService.verifyEmail(userEmail, hash));
    }


}
