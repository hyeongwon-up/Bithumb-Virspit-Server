package com.virspit.virspituser.domain.member.service;

import com.virspit.virspituser.domain.member.dto.request.InitPwdRequestDto;
import com.virspit.virspituser.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspituser.domain.member.dto.response.MemberInfoResponseDto;
import com.virspit.virspituser.domain.member.entity.Gender;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.entity.Role;
import com.virspit.virspituser.domain.member.feign.AuthServiceFeignClient;
import com.virspit.virspituser.domain.member.repository.MemberRepository;
import com.virspit.virspituser.domain.wallet.entity.Wallet;
import com.virspit.virspituser.domain.wallet.service.WalletService;
import com.virspit.virspituser.global.error.exception.EntityNotFoundException;
import jnr.a64asm.Mem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private WalletService walletService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AuthServiceFeignClient authServiceFeignClient;

    @InjectMocks
    private MemberService memberService;


    private Wallet wallet = new Wallet();
    private Member member = new Member();

    @BeforeEach
    void setUp() {

        member = Member.builder()
                .memberName("testMember")
                .email("test@test.com")
                .password("password")
                .gender(Gender.ETC)
                .birthdayDate(LocalDate.now())
                .role(Role.USER)
                .wallet(wallet)
                .build();
    }


    @Test
    void registry() throws ApiException {

        //given
        MemberSignUpRequestDto memberSignUpRequestDto =
                new MemberSignUpRequestDto();
        memberSignUpRequestDto.setMemberName("testMember");
        memberSignUpRequestDto.setEmail("test@test.com");
        memberSignUpRequestDto.setBirthdayDate(LocalDate.of(1996, 12, 28));
        memberSignUpRequestDto.setPassword("password");
        memberSignUpRequestDto.setGender(Gender.ETC);
        memberSignUpRequestDto.setPhoneNumber("01096394624");

        given(walletService.createWallet()).willReturn(wallet);
        given(memberRepository.save(any())).willReturn(member);

        //when

        MemberInfoResponseDto result = memberService.registry(memberSignUpRequestDto);

        //then
        assertThat(result.getMemberName()).isEqualTo(member.getMemberName());
        assertThat(result.getRole()).isEqualTo(Role.USER);

    }

    @Test
    void findByEmail_성공() {
        //given
        given(memberRepository.findByEmail("testEmail")).willReturn(java.util.Optional.ofNullable(member));

        //when
        Member result = memberService.findByEmail("testEmail");

        //then
        assertThat(result.getMemberName()).isEqualTo("testMember");
        assertThat(result.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void findByEmail_실패() {
        //given
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> {
            memberService.findByEmail("test@test.com");
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void edit() {

        //given
        member = Member.builder()
                .memberName("editName")
                .email("editEmail")
                .password("editPassword")
                .gender(Gender.ETC)
                .birthdayDate(LocalDate.now())
                .wallet(wallet)
                .build();

        given(memberRepository.save(any())).willReturn(member);

        //when
        String result = memberService.edit(member);

        //then
        assertThat(result).isEqualTo("저장하였습니다.");
    }


    @Test
    void initPwd() {
        //given
        InitPwdRequestDto initPwdRequestDto = new InitPwdRequestDto();
        initPwdRequestDto.setEmail("test@test.com");
        initPwdRequestDto.setPassword("password");

        given(memberRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(member));

        //when
        Boolean result = memberService.initPwd(initPwdRequestDto);

        //then
        assertThat(result).isEqualTo(true);
    }




}