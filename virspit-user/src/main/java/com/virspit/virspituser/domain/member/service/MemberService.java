package com.virspit.virspituser.domain.member.service;

import com.virspit.virspituser.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public String save(MemberSignUpRequestDto memberSignUpRequestDto) {


        Member member = Member.builder()
                .username(memberSignUpRequestDto.getUsername())
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
}
