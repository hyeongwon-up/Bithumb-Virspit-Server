package com.virspit.virspitauth.service;

import com.virspit.virspitauth.feign.MemberServiceFeignClient;
import com.virspit.virspitauth.jwt.JwtGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private JwtUserDetailsService jwtUserDetailsService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RedisTemplate<String, Integer> verifyRedisTemplate;
    @Mock
    private JwtGenerator jwtGenerator;
    @Mock
    private StringRedisTemplate stringRedisTemplate;
    @Mock
    private JavaMailSenderImpl javaMailSender;
    @Mock
    private MemberServiceFeignClient memberServiceFeignClient;
    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void register() {
    }

    @Test
    void login() {
    }

    @Test
    void verifyUserEmail() {
    }

    @Test
    void verifyNumber() {
    }

    @Test
    void findPasssword() {
    }

    @Test
    void getSHA512Token() {
    }

    @Test
    void initPassword() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void logout() {
    }

    @Test
    void checkFeign() {
    }
}