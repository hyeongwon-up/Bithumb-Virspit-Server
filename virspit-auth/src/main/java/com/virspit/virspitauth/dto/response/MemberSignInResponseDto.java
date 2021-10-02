package com.virspit.virspitauth.dto.response;

import com.virspit.virspitauth.dto.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberSignInResponseDto {
    private String email;
    private String accessToken;
    private String refreshToken;
}
