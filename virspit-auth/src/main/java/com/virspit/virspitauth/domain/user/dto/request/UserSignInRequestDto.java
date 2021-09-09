package com.virspit.virspitauth.domain.user.dto.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignInRequestDto {
    private String email;
    private String password;
}
