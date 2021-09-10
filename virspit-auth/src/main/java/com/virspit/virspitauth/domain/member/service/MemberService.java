package com.virspit.virspitauth.domain.member.service;

import com.virspit.virspitauth.domain.member.dto.request.MemberSignInRequestDto;
import com.virspit.virspitauth.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.domain.member.entity.Member;
import com.virspit.virspitauth.domain.member.repository.MemberRepository;
import com.virspit.virspitauth.exception.SecurityRuntimeException;
import com.virspit.virspitauth.jwt.JwtGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    private final JwtUserDetailsService jwtUserDetailsService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final RedisTemplate<String, Object> memberRedisTemplate;
    private final StringRedisTemplate stringRedisTemplate;


    public String signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
        log.info("################## 회원가입 Service Start ###########");
        if (!memberRepository.existsByEmail(memberSignUpRequestDto.getEmail())) {

            Member member = Member.builder()
                    .username(memberSignUpRequestDto.getUsername())
                    .email(memberSignUpRequestDto.getEmail())
                    .password(passwordEncoder.encode(memberSignUpRequestDto.getPassword()))
                    .gender(memberSignUpRequestDto.getGender())
                    .birthdayDate(memberSignUpRequestDto.getBirthdayDate())
                    .build();

            ValueOperations<String, Object> vop = memberRedisTemplate.opsForValue();
            vop.set("toverify-" + memberSignUpRequestDto.getEmail(), member);

            memberRepository.save(member);
            return "success";
        } else {
            throw new SecurityRuntimeException("중복된 ID 입니다", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public MemberSignInRequestDto singIn(MemberSignInRequestDto memberSignInRequestDto) {
        try {
            final String email = memberSignInRequestDto.getEmail();
            Member member = memberRepository.findByEmail(email);

            if (stringRedisTemplate.opsForValue().get("email-" + email) != null) {
                throw new SecurityRuntimeException("errorCode", HttpStatus.BAD_REQUEST);
            }

            member.setAccess_dt(new Date());
            memberRepository.save(member);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, memberSignInRequestDto.getPassword()));

            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
            final String accessToken = jwtGenerator.generateAccessToken(userDetails);
            final String refreshToken = jwtGenerator.generateRefreshToken(email);

            //generate Token and save in redis
            stringRedisTemplate.opsForValue().set("refresh-" + email, refreshToken);

            log.info("generated access Token : " + accessToken);
            log.info("generated refresh Token : " + refreshToken);

            return memberSignInRequestDto;
        } catch (Exception e) {
            throw new SecurityRuntimeException("유효하지 않은 아이디 / 비밀번호", HttpStatus.UNPROCESSABLE_ENTITY);
        }


    }

    public Map<String, Object> addNewUser(MemberSignUpRequestDto memberSignUpRequestDto) {
        String email = memberSignUpRequestDto.getEmail();
        Map<String, Object> map = new HashMap<>();

        System.out.println("회원가입요청 아이디: " + email + "비번: " + memberSignUpRequestDto.getPassword());

        Member member = memberRepository.save(
                Member.builder()
                        .username(memberSignUpRequestDto.getUsername())
                        .email(memberSignUpRequestDto.getEmail())
                        .password(passwordEncoder.encode(memberSignUpRequestDto.getPassword()))
                        .gender(memberSignUpRequestDto.getGender())
                        .birthdayDate(memberSignUpRequestDto.getBirthdayDate())
                        .build()
        );

        ValueOperations<String, Object> vop = memberRedisTemplate.opsForValue();
        vop.set("toverify-" + email, member);
        return map;
    }

    public Map<String, Object> login(MemberSignInRequestDto memberSignInRequestDto) {
        Map<String, Object> map = new HashMap<>();
        final String email = memberSignInRequestDto.getEmail();
        Member member = memberRepository.findByEmail(email);

        if (stringRedisTemplate.opsForValue().get("email-" + email) != null) {
            map.put("errorCode", 69);
            return map;
        }
        member.setAccess_dt(new Date());
        memberRepository.save(member);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, memberSignInRequestDto.getPassword()));

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
        final String accessToken = jwtGenerator.generateAccessToken(userDetails);
        final String refreshToken = jwtGenerator.generateRefreshToken(email);

        //generate Token and save in redis
        stringRedisTemplate.opsForValue().set("refresh-" + email, refreshToken);

        logger.info("generated access token: " + accessToken);
        logger.info("generated refresh token: " + refreshToken);
        map.put("errorCode", 10);
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return map;
    }


}
