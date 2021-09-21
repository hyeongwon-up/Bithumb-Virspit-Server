package com.virspit.virspituser.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.spi.Toolable;
import com.virspit.virspituser.global.code.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MemberSignUpRequestDto {

    private String username;

    private String email;

    private String password;

    private Gender gender;

    private LocalDate birthdayDate;

}
