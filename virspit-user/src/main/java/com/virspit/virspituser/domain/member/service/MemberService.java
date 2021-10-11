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
import com.virspit.virspituser.global.error.ErrorCode;
import com.virspit.virspituser.global.error.exception.EntityNotFoundException;
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
        Member member =  memberRepository.findByEmail(memberEmail)
                .orElseThrow(() ->new EntityNotFoundException(memberEmail));
        log.info("member : " + member.toString());
        return member;
    }

    public String edit(@RequestBody Member member) {
        memberRepository.save(member);
        return "저장하였습니다.";
    }

    public Boolean initPwd(InitPwdRequestDto initPwdRequestDto) {

        Member member = memberRepository.findByEmail(initPwdRequestDto.getEmail())
                .orElseThrow(() ->new EntityNotFoundException(initPwdRequestDto.getEmail()));
        member.changePwd(initPwdRequestDto.getPassword());
        memberRepository.save(member);

        return true;
    }

    public String changeMemberInfo(Long memberId, MemberEditInfoRequestDto memberEditInfoRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(memberId.toString()));
        member.editInfo(memberEditInfoRequestDto);
        memberRepository.save(member);
        return "성공";
    }

    public String changePwd(MemberChangePwdRequestDto memberChangePwdRequestDto) {
        memberRepository.save(authServiceFeignClient.changePassword(memberChangePwdRequestDto));
        return "ok";
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public MemberInfoResponseDto MemberInfoFindById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));

        return MemberInfoResponseDto.of(member);
    }

    public boolean checkByEmail(String memberEmail) {
        log.info("이메일 중복 체크 : " + memberEmail);

        if(memberRepository.findByEmail(memberEmail).isEmpty()) {
            return true;
        } else {
            log.warn("이미 가입한 계정으로 가입요청 : " + memberEmail);
            return false;
        }
    }
}
