package com.virspit.virspitauth.controller;

import com.virspit.virspitauth.common.SuccessResponse;
import com.virspit.virspitauth.dto.model.Member;
import com.virspit.virspitauth.dto.request.MemberChangePwdRequestDto;
import com.virspit.virspitauth.dto.request.MemberSignInRequestDto;
import com.virspit.virspitauth.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.dto.response.MemberSignInResponseDto;
import com.virspit.virspitauth.dto.response.MemberSignUpResponseDto;
import com.virspit.virspitauth.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;


    @PostMapping("/register")
    @ApiOperation("회원가입")
    public SuccessResponse<MemberSignUpResponseDto> addNewUser(@RequestBody MemberSignUpRequestDto memberSignUpRequestDto) {
        return SuccessResponse.of(memberService.register(memberSignUpRequestDto));
    }

    @PostMapping("/signin")
    @ApiOperation("로그인")
    public SuccessResponse<MemberSignInResponseDto> login(@RequestBody MemberSignInRequestDto memberSignInRequestDto) throws Exception {
        return SuccessResponse.of(memberService.login(memberSignInRequestDto));
    }

    @PostMapping("/signout")
    @ApiOperation("로그아웃")
    public SuccessResponse<String> logout(String accessToken) {
        return SuccessResponse.of(memberService.logout(accessToken));
    }

    @GetMapping("/verify/mail")
    @ApiOperation("회원가입시 입력한 이메일 인증")
    public SuccessResponse<String> verifyEmail(@RequestParam("useremail") String userEmail) throws Exception {
        return SuccessResponse.of(memberService.verifyUserEmail(userEmail));
    }

    @PostMapping("/verify/mail")
    @ApiOperation("이메일에 전송된 인증번호 검증")
    public SuccessResponse<Boolean> verifyNumber(@RequestParam("useremail") String userEmail, Integer number) throws Exception {
        return SuccessResponse.of(memberService.verifyNumber(userEmail, number));
    }

    @PostMapping("/initpwd")
    @ApiOperation("비밀번호 잃어버렸을 때 초기화 요청")
    public SuccessResponse<Boolean> findPassword(@RequestParam("useremail") String userEmail) throws Exception {
        return SuccessResponse.of(memberService.findPasssword(userEmail));
    }

    @GetMapping("/findpwd/res")
    @ApiOperation("비밀번호 초기화 요청 후 응답")
    public ResponseEntity<Boolean> initPassword(
            @RequestParam("useremail") String userEmail, @RequestParam("key") String hash) throws Exception {
        return ResponseEntity.ok(memberService.initPassword(userEmail, hash));
    }

    @PutMapping("/changepwd")
    @ApiOperation("비밀번호 변경")
    public SuccessResponse<Boolean> changePassword(@RequestBody MemberChangePwdRequestDto memberChangePwdRequestDto) {
        return SuccessResponse.of(memberService.changePassword(memberChangePwdRequestDto));
    }


}
