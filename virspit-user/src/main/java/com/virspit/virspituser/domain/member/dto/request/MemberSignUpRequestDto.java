package com.virspit.virspituser.domain.member.dto.request;

import com.virspit.virspituser.domain.member.entity.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MemberSignUpRequestDto {

    private String memberName;

    private String email;

    private String password;

    private Gender gender;

    private LocalDate birthdayDate;

}
