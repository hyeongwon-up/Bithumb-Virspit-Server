package com.virspit.virspitauth.dto.request;

import com.virspit.virspitauth.dto.model.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

