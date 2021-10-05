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
public class MemberSignUpResponseDto {
    private String memberName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private LocalDate birthdayDate;
    private Role role;


    MemberSignUpResponseDto(Member member) {
        memberName = member.getMemberName();
        email = member.getEmail();
        gender = member.getGender();
        birthdayDate = member.getBirthdayDate();
        role = member.getRole();
    }

    public static MemberSignUpResponseDto of(Member member) {
        return new MemberSignUpResponseDto(member);
    }
}
