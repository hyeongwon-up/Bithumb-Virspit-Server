package com.virspit.virspitauth.dto.request;

import com.virspit.virspitauth.dto.model.Gender;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MemberSignUpRequestDto {

    @NotNull(message = "이름을 입력해주세요.")
    private String memberName;

    @NotNull(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    @NotNull(message = "핸드폰 번호를 입력해주세요.")
    private String phoneNumber;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호를 8~10 자 사이로 입력해주세요")
    private String password;

    @NotNull(message = "성별을 입력해주세요.")
    private Gender gender;

    @NotNull(message = "생일을 입력해주세요.")
    private LocalDate birthdayDate;

}

