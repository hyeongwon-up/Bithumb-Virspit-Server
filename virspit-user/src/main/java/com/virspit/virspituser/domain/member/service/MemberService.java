package com.virspit.virspituser.domain.member.service;

import com.virspit.virspituser.domain.member.dto.request.MemberEditInfoRequestDto;
import com.virspit.virspituser.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public String registry(MemberSignUpRequestDto memberSignUpRequestDto) {


        Member member = Member.builder()
                .memberName(memberSignUpRequestDto.getMemberName())
                .email(memberSignUpRequestDto.getEmail())
                .password(memberSignUpRequestDto.getPassword())
                .gender(memberSignUpRequestDto.getGender())
                .birthdayDate(memberSignUpRequestDto.getBirthdayDate())
                .build();


        memberRepository.save(member);

        return "ok";
    }

    public Member findByEmail(String memberEmail) {
        return memberRepository.findByEmail(memberEmail);
    }

    public String changePwd(@RequestBody Member member) {
        memberRepository.save(member);
        return "저장하였습니다.";
    }

    public String changeMemberInfo(Long memberId, MemberEditInfoRequestDto memberEditInfoRequestDto) {

        Member member = memberRepository.findById(memberId).get();
        member.editInfo(memberEditInfoRequestDto);
        memberRepository.save(member);
        return "성공";
    }

}
