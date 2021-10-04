package com.virspit.virspitauth.dto.request;

import com.virspit.virspitauth.dto.model.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
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

