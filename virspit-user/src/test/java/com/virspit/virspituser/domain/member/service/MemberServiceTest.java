package com.virspit.virspituser.domain.member.service;

import com.virspit.virspituser.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspituser.domain.member.entity.Gender;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.entity.Role;
import com.virspit.virspituser.domain.member.feign.AuthServiceFeignClient;
import com.virspit.virspituser.domain.member.repository.MemberRepository;
import com.virspit.virspituser.domain.wallet.entity.Wallet;
import com.virspit.virspituser.domain.wallet.service.WalletService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
                .memberName("testName")
                .email("testEmail")
                .password("testPassword")
                .gender(Gender.ETC)
                .birthdayDate(LocalDate.now())
                .wallet(wallet)
                .build();
    }


    @Test
    void registry() throws ApiException {

        //given
        MemberSignUpRequestDto memberSignUpRequestDto
                = MemberSignUpRequestDto.builder()
                .memberName("testName")
                .email("testEmail")
                .password("testPassword")
                .gender(Gender.ETC)
                .birthdayDate(LocalDate.now())
                .build();

        given(walletService.createWallet()).willReturn(wallet);
        given(memberRepository.save(any())).willReturn(member);

        //when
        String result = memberService.registry(memberSignUpRequestDto);

        //then
        assertThat(result).isEqualTo("ok");

    }

    @Test
    void findByEmail() {
        //given
        given(memberRepository.findByEmail("testEmail")).willReturn(member);

        //when
        Member result = memberService.findByEmail("testEmail");

        //then
        assertThat(result.getMemberName()).isEqualTo("testName");
        assertThat(result.getRole()).isEqualTo(Role.USER);
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


}