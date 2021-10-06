package com.virspit.virspitauth.dto.response;


import com.virspit.virspitauth.dto.model.Gender;
import com.virspit.virspitauth.dto.model.Member;
import com.virspit.virspitauth.dto.model.Role;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberInfoResponseDto {
    private Long id;
    private String memberName;
    private String email;
    private Gender gender;
    private String phoneNumber;
    private LocalDate birthdayDate;
    private Role role;

    MemberInfoResponseDto(Member member) {
        id = member.getId();
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
