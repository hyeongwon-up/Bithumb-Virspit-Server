package com.virspit.virspitauth.service;

import com.virspit.virspitauth.dto.model.Gender;
import com.virspit.virspitauth.dto.model.Member;
import com.virspit.virspitauth.dto.model.Role;
import com.virspit.virspitauth.dto.request.MemberSignInRequestDto;
import com.virspit.virspitauth.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.dto.response.MemberInfoResponseDto;
import com.virspit.virspitauth.error.exception.InvalidValueException;
import com.virspit.virspitauth.feign.MemberServiceFeignClient;
import com.virspit.virspitauth.jwt.JwtGenerator;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    Member member = new Member();

    @BeforeEach
    void setUp() {
        memberSignUpRequestDto.setMemberName("testMember");
        memberSignUpRequestDto.setPhoneNumber("01096394624");
        memberSignUpRequestDto.setEmail("test@test.com");
        memberSignUpRequestDto.setPassword("password");
        memberSignUpRequestDto.setGender(Gender.ETC);
        memberSignUpRequestDto.setBirthdayDate(LocalDate.of(1996, 12, 28));

        member = Member.builder()
                .id(1L)
                .memberName("testMember")
                .email("test@test.com")
                .password("password")
                .phoneNumber("01096394624")
                .gender(Gender.ETC)
                .birthdayDate(LocalDate.of(1996, 12, 28))
                .wallet(null)
                .build();


        Mockito.clearInvocations();
    }

    @Test
    void register_성공() {
        //given
        MemberInfoResponseDto memberInfoResponseDto = MemberInfoResponseDto.of(member);
        given(memberServiceFeignClient.save(memberSignUpRequestDto)).willReturn(memberInfoResponseDto);

        //when
        MemberInfoResponseDto result = memberService.register(memberSignUpRequestDto);

        //then
        assertThat(result.getMemberName()).isEqualTo(memberSignUpRequestDto.getMemberName());
        assertThat(result.getRole()).isEqualTo(Role.USER);

    }

    @Test
    void login_실패_블랙리스트() {
        //given
        MemberSignInRequestDto memberSignInRequestDto = new MemberSignInRequestDto();
        memberSignInRequestDto.setEmail("test@test.com");
        memberSignInRequestDto.setPassword("password");

        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        Mockito.doReturn("aa").when(valueOperations).get(Mockito.any());


        //when, then
        assertThatThrownBy(() -> {
           memberService.login(memberSignInRequestDto);
        }).isInstanceOf(InvalidValueException.class);

    }

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