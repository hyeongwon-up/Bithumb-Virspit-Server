package com.virspit.virspituser.domain.member.controller;

import com.virspit.virspituser.domain.member.dto.request.MemberChangePwdRequestDto;
import com.virspit.virspituser.domain.member.dto.request.MemberEditInfoRequestDto;
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
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;

import javax.ws.rs.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @ApiOperation("memberId로 member 찾기")
    @GetMapping("/{id}")
    public Member findById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @ApiOperation("회원가입 요청한 Member를 db에 저장")
    @PostMapping("/save")
    public String save(@RequestBody MemberSignUpRequestDto memberSignUpRequestDto) throws ApiException {
        return memberService.registry(memberSignUpRequestDto);
    }

    @ApiOperation("email을 통해 member 정보 전달")
    @GetMapping
    public Member findByEmail(@RequestParam String memberEmail) {
        log.info("find email controller start");
        return memberService.findByEmail(memberEmail);
    }

    @ApiOperation("Member 비밀번호 초기화 저장")
    @PostMapping("/pwd")
    public String edit(@RequestBody Member member) {
        return memberService.edit(member);
    }

    @ApiOperation("회원 정보 수정 (이름, 성별, 생년월일)")
    @PutMapping("/edit/info/{id}")
    public ResponseEntity<String> changeMemberInfo(@PathVariable("id") Long memberId,
                                                   @RequestBody MemberEditInfoRequestDto memberEditInfoRequestDto) {
        return ResponseEntity.ok(memberService.changeMemberInfo(memberId, memberEditInfoRequestDto));
    }

    @ApiOperation("회원 비밀번호 변경")
    @PutMapping("/edit/pwd")
    public ResponseEntity<String> changePwd(@RequestBody MemberChangePwdRequestDto memberChangePwdRequestDto) {
        return ResponseEntity.ok(memberService.changePwd(memberChangePwdRequestDto));
    }

}
