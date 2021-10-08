package com.virspit.virspitauth.dto.request;

import lombok.Data;

@Data
public class NewAccessTokenRequestDto {
    private String accessToken;
    private String refreshToken;
}
