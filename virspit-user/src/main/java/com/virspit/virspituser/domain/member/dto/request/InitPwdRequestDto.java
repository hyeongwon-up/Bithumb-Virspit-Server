package com.virspit.virspituser.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class InitPwdRequestDto {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
}
