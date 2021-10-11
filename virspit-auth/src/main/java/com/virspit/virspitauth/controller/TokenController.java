package com.virspit.virspitauth.controller;

import com.virspit.virspitauth.common.SuccessResponse;
import com.virspit.virspitauth.dto.request.CheckTokenRequestDto;
import com.virspit.virspitauth.dto.request.NewAccessTokenRequestDto;
import com.virspit.virspitauth.service.TokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping(path = "/token")
    @ApiOperation("유효한 AccessToken인지 검증")
    public SuccessResponse<Boolean> checkAccessToken(@RequestBody CheckTokenRequestDto checkTokenRequestDto) {
        return SuccessResponse.of(tokenService.checkAccessToken(checkTokenRequestDto));
    }

    @PostMapping(path = "/refresh")
    @ApiOperation("RefreshToken 으로 AccessToken 재발급")
    public SuccessResponse<?> requestForNewAccessToken(@RequestBody NewAccessTokenRequestDto newAccessTokenRequestDto) {
        return SuccessResponse.of(tokenService.requestForNewAccessToken(newAccessTokenRequestDto));
    }

}
