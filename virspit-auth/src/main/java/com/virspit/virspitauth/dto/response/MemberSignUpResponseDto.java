package com.virspit.virspitauth.dto.response;


import com.virspit.virspitauth.dto.model.Gender;
import com.virspit.virspitauth.dto.model.Member;
import com.virspit.virspitauth.dto.model.Role;
import com.virspit.virspitauth.dto.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MemberSignUpResponseDto {
    private String memberName;
    private String email;
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
