package com.virspit.virspitauth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InitPwdRequestDto {
    private String email;
    private String password;
}
