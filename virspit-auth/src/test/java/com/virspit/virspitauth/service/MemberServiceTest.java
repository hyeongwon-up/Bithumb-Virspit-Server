package com.virspit.virspitauth.service;

import com.virspit.virspitauth.dto.model.Gender;
import com.virspit.virspitauth.dto.model.Role;
import com.virspit.virspitauth.dto.request.MemberSignInRequestDto;
import com.virspit.virspitauth.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.dto.response.MemberSignInResponseDto;
import com.virspit.virspitauth.dto.response.MemberSignUpResponseDto;
import com.virspit.virspitauth.error.exception.InvalidValueException;
import com.virspit.virspitauth.feign.MemberServiceFeignClient;
import com.virspit.virspitauth.jwt.JwtGenerator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

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
    @Mock
    private ValueOperations valueOperations;
    @InjectMocks
    private MemberService memberService;


    MemberSignUpRequestDto memberSignUpRequestDto = new MemberSignUpRequestDto();


    @BeforeEach
    void setUp() {
        memberSignUpRequestDto.setMemberName("testMember");
        memberSignUpRequestDto.setEmail("test@test.com");
        memberSignUpRequestDto.setPassword("password");
        memberSignUpRequestDto.setGender(Gender.ETC);
        memberSignUpRequestDto.setBirthdayDate(LocalDate.of(1996, 12, 28));


        Mockito.clearInvocations();
    }

//    @Test
//    void register_성공() {
//        //given
////        MemberSignUpResponseDto memberSignUpResponseDto =
////                new MemberSignUpResponseDto(
////                        "testMember","test@test.com",Gender.ETC,
////                        LocalDate.of(1996,12,28), Role.USER);
//
//        given(memberServiceFeignClient.save(memberSignUpRequestDto)).willReturn(memberSignUpResponseDto);
//
//        //when
//        MemberSignUpResponseDto result = memberService.register(memberSignUpRequestDto);
//
//        //then
//        assertThat(result.getMemberName()).isEqualTo(memberSignUpRequestDto.getMemberName());
//        assertThat(result.getRole()).isEqualTo(Role.USER);
//
//    }

//    @Test
//    void login_실패_블랙리스트() {
//        //given
//        MemberSignInRequestDto memberSignInRequestDto =
//                new MemberSignInRequestDto("test@test.com",  "password");
//
//        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
//        Mockito.doReturn("aa").when(valueOperations).get(Mockito.any());
//
//
//        //when, then
//        assertThatThrownBy(() -> {
//           memberService.login(memberSignInRequestDto);
//        }).isInstanceOf(InvalidValueException.class);

//    }

//    @Test
//    void login_실패_비밀번호틀림(){
//        //given
//        MemberSignInRequestDto memberSignInRequestDto =
//                new MemberSignInRequestDto("test@test.com",  "password");
//
//        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
//        Mockito.doReturn("").when(valueOperations).get(any());
//
//    }




}