package com.virspit.virspitauth.dto.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberSignInRequestDto {
    private String email;
    private String password;
}
