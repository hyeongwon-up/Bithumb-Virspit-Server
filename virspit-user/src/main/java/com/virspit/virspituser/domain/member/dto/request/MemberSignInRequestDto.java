package com.virspit.virspituser.domain.member.dto.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberSignInRequestDto {
    private String email;
    private String password;
}
