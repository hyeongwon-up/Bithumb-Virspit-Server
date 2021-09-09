package com.virspit.virspitauth.domain.member.service;

import com.virspit.virspitauth.domain.member.dto.request.MemberSignInRequestDto;
import com.virspit.virspitauth.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.domain.member.entity.Member;
import com.virspit.virspitauth.domain.member.repository.MemberRepository;
import com.virspit.virspitauth.exception.SecurityRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> memberRedisTemplate;




    public String signUp(MemberSignUpRequestDto memberSignUpRequestDto){
        log.info("################## 회원가입 Service Start ###########");
        if(!memberRepository.existsByEmail(memberSignUpRequestDto.getEmail())){

            Member member = Member.builder()
                    .username(memberSignUpRequestDto.getUsername())
                    .email(memberSignUpRequestDto.getEmail())
                    .password(passwordEncoder.encode(memberSignUpRequestDto.getPassword()))
                    .gender(memberSignUpRequestDto.getGender())
                    .birthdayDate(memberSignUpRequestDto.getBirthdayDate())
                    .build();

            memberRepository.save(member);
            return "success";
        }else{
            throw new SecurityRuntimeException("중복된 ID 입니다", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public MemberSignInRequestDto singIn(MemberSignInRequestDto memberSignInRequestDto) {
        try{
            String token = (passwordEncoder.matches(
                    memberSignInRequestDto.getPassword(),
                    memberRepository.findByEmail(memberSignInRequestDto.getEmail()).getPassword()))
                    ? provider.createToken(userSignInRequestDto.getEmail(), userRepository.findByEmail(userSignInRequestDto.getEmail()).get().getRoles())
                    : "Wrong Password";
            log.info("token : " + token);

            UserSignInResponseDto userSignInResponseDto = new UserSignInResponseDto(token);
            log.info("return : " + userSignInResponseDto);
            return userSignInResponseDto;

        }catch (Exception e){
            throw new SecurityRuntimeException("유효하지 않은 아이디 / 비밀번호", HttpStatus.UNPROCESSABLE_ENTITY);
        }


    }




}
