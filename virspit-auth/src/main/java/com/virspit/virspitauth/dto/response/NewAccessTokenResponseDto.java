package com.virspit.virspitauth.dto.response;

import lombok.Data;

@Data
public class NewAccessTokenResponseDto {
    private String accessToken;

    public NewAccessTokenResponseDto(String newAccessToken) {
        this.accessToken = newAccessToken;
    }
}
