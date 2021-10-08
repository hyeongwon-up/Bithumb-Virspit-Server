package com.virspit.virspitauth.service;

import com.virspit.virspitauth.dto.request.NewAccessTokenRequestDto;
import com.virspit.virspitauth.dto.response.NewAccessTokenResponseDto;
import com.virspit.virspitauth.error.ErrorCode;
import com.virspit.virspitauth.error.exception.TokenException;
import com.virspit.virspitauth.jwt.JwtGenerator;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final JwtGenerator jwtGenerator;
    private final StringRedisTemplate stringRedisTemplate;
    private final JwtUserDetailsService userDetailsService;



    public Boolean checkAccessToken(String accessToken) {
        String username = null;

        try {
            username = jwtGenerator.getUsernameFromToken(accessToken);
            log.info("AccesToken 검증 성공 UserName : " + username);
        } catch (ExpiredJwtException e) {
            username = e.getClaims().getSubject();
            throw new TokenException(ErrorCode.ACCESS_TOKEN_IS_EXPIRED);
        }

        return true;
    }


    public Object requestForNewAccessToken(NewAccessTokenRequestDto newAccessTokenRequestDto) {
        String username = null;

        String expiredAccessToken = newAccessTokenRequestDto.getAccessToken();
        String refreshToken = newAccessTokenRequestDto.getRefreshToken();

        try {
            username = jwtGenerator.getUsernameFromToken(expiredAccessToken);
        } catch (ExpiredJwtException e) {
            username = e.getClaims().getSubject();
            log.info("username from expired access token: " + username);
        }

        if (username == null) throw new IllegalArgumentException();

        String refreshTokenFromDb = stringRedisTemplate.opsForValue().get("refresh-"+username);
        log.info("refreshToken from redis: " + refreshTokenFromDb);

        //user refresh token doesn't match with cache
        if (!refreshToken.equals(refreshTokenFromDb)) {
            throw new TokenException(ErrorCode.TOKEN_IS_NOT_VALID);
        }
        //refresh token is expired
        if (jwtGenerator.isTokenExpired(refreshToken)) {
            throw new TokenException(ErrorCode.REFRESH_TOKEN_IS_EXPIRED);
        }

        //generate access token if valid refresh token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String newAccessToken =  jwtGenerator.generateAccessToken(userDetails);

        return new NewAccessTokenResponseDto(newAccessToken);
    }
}
