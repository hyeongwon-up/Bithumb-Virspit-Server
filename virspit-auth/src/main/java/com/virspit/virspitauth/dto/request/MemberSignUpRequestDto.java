package com.virspit.virspitauth.dto.request;

import com.virspit.virspitauth.dto.model.Gender;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
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

