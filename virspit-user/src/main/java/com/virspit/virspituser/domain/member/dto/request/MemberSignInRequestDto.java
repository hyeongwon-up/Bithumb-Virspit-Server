package com.virspit.virspituser.domain.member.dto.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@RequiredArgsConstructor
public class MemberSignInRequestDto {
    @Email(message = "이메일 형식을 지켜주세요.")
    private String email;
    private String password;
}
