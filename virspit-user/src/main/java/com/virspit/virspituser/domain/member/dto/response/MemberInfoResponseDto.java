package com.virspit.virspituser.domain.member.dto.response;


import com.virspit.virspituser.domain.member.entity.Gender;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberInfoResponseDto {
    private String memberName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private LocalDate birthdayDate;
    private Role role;


    MemberInfoResponseDto(Member member) {
        memberName = member.getMemberName();
        email = member.getEmail();
        gender = member.getGender();
        birthdayDate = member.getBirthdayDate();
        phoneNumber = member.getPhoneNumber();
        role = member.getRole();
    }

    public static MemberInfoResponseDto of(Member member) {
        return new MemberInfoResponseDto(member);
    }
}
