package com.virspit.virspitauth.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberSignInResponseDto {
    String accessToken;
    String refreshToken;
}
