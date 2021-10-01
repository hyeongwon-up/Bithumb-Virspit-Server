package com.virspit.virspituser.domain.member.dto.request;

import com.virspit.virspituser.domain.member.entity.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class MemberSignUpRequestDto {

    @NotNull
    private String memberName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate birthdayDate;

}
