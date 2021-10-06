package com.virspit.virspituser.domain.member.service;

import com.virspit.virspituser.domain.member.dto.request.InitPwdRequestDto;
import com.virspit.virspituser.domain.member.dto.request.MemberChangePwdRequestDto;
import com.virspit.virspituser.domain.member.dto.request.MemberEditInfoRequestDto;
import com.virspit.virspituser.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspituser.domain.member.dto.response.MemberInfoResponseDto;
import com.virspit.virspituser.domain.member.entity.Member;
import com.virspit.virspituser.domain.member.entity.Role;
import com.virspit.virspituser.domain.member.feign.AuthServiceFeignClient;
import com.virspit.virspituser.domain.member.repository.MemberRepository;
import com.virspit.virspituser.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final WalletService walletService;
    private final AuthServiceFeignClient authServiceFeignClient;


    public MemberInfoResponseDto registry(MemberSignUpRequestDto memberSignUpRequestDto) throws ApiException {

        Member member = Member.builder()
                .memberName(memberSignUpRequestDto.getMemberName())
                .email(memberSignUpRequestDto.getEmail())
                .phoneNumber(memberSignUpRequestDto.getPhoneNumber())
                .password(memberSignUpRequestDto.getPassword())
                .gender(memberSignUpRequestDto.getGender())
                .birthdayDate(memberSignUpRequestDto.getBirthdayDate())
                .role(Role.USER)
                .wallet(walletService.createWallet())
                .build();

        return MemberInfoResponseDto.of(memberRepository.save(member));

    }

    public Member findByEmail(String memberEmail) {
        Member member =  memberRepository.findByEmail(memberEmail);
        log.info("member : " + member.toString());
        return member;
    }

    public String edit(@RequestBody Member member) {
        memberRepository.save(member);
        return "저장하였습니다.";
    }

    public Boolean initPwd(InitPwdRequestDto initPwdRequestDto) {

        Member member = memberRepository.findByEmail(initPwdRequestDto.getEmail());
        member.changePwd(initPwdRequestDto.getPassword());
        memberRepository.save(member);

        return true;
    }

    public String changeMemberInfo(Long memberId, MemberEditInfoRequestDto memberEditInfoRequestDto) {
        Member member = memberRepository.findById(memberId).get();
        member.editInfo(memberEditInfoRequestDto);
        memberRepository.save(member);
        return "성공";
    }

    public String changePwd(MemberChangePwdRequestDto memberChangePwdRequestDto) {
        memberRepository.save(authServiceFeignClient.changePassword(memberChangePwdRequestDto));
        return "ok";
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).get();
    }
}
