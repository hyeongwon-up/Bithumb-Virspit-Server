package com.virspit.virspitauth.domain.member.service;

import com.sun.jersey.api.MessageException;
import com.virspit.virspitauth.domain.member.dto.request.MemberSignInRequestDto;
import com.virspit.virspitauth.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.domain.member.dto.response.MemberSignInResponseDto;
import com.virspit.virspitauth.domain.member.entity.Member;
import com.virspit.virspitauth.domain.member.repository.MemberRepository;
import com.virspit.virspitauth.exception.SecurityRuntimeException;
import com.virspit.virspitauth.jwt.JwtGenerator;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final RedisTemplate<String, Object> memberRedisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final JavaMailSenderImpl javaMailSender;

    @Value("${my.ip}")
    private String myIp;

    public MemberSignInResponseDto singIn(MemberSignInRequestDto memberSignInRequestDto) {
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


            return new MemberSignInResponseDto(accessToken, refreshToken);
        } catch (Exception e) {
            throw new SecurityRuntimeException("유효하지 않은 아이디 / 비밀번호", HttpStatus.UNPROCESSABLE_ENTITY);
        }


    }

    public String register(MemberSignUpRequestDto memberSignUpRequestDto) {
        String email = memberSignUpRequestDto.getEmail();
        System.out.println("회원가입요청 아이디: " + email + "비번: " + memberSignUpRequestDto.getPassword());

        Member member = Member.builder()
                        .username(memberSignUpRequestDto.getUsername())
                        .email(memberSignUpRequestDto.getEmail())
                        .password(passwordEncoder.encode(memberSignUpRequestDto.getPassword()))
                        .gender(memberSignUpRequestDto.getGender())
                        .birthdayDate(memberSignUpRequestDto.getBirthdayDate())
                        .build();

        try{
            sendMail(member.getEmail(), member.getUsername(), 0);
        } catch(MessagingException e) {
            return "에러발생";
        } catch (Exception e) {
            return "에러발생";
        }

        ValueOperations<String, Object> vop = memberRedisTemplate.opsForValue();
        vop.set("toverify-" + email, member);
        return "회원가입 요청 성공";
    }

    public MemberSignInResponseDto login(MemberSignInRequestDto memberSignInRequestDto) throws Exception {
        Map<String, Object> map = new HashMap<>();
        final String email = memberSignInRequestDto.getEmail();
        Member member = memberRepository.findByEmail(email);

        if (stringRedisTemplate.opsForValue().get("email-" + email) != null) {
            throw new Exception("잘못된 정보 임다");
        }
        member.setAccess_dt(new Date());
        memberRepository.save(member);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, memberSignInRequestDto.getPassword()));

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
        final String accessToken = jwtGenerator.generateAccessToken(userDetails);
        final String refreshToken = jwtGenerator.generateRefreshToken(email);

        //generate Token and save in redis
        stringRedisTemplate.opsForValue().set("refresh-" + email, refreshToken);


        return new MemberSignInResponseDto(accessToken, refreshToken);
    }

    @Async
    public void sendMail(String email, String username, int type) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("[본인인증] VIRSPIT 이메일 인증");
        int rand = new Random().nextInt(999999);
        String formatted = String.format("%06d",rand);
        String hash = getSHA512Token(username, formatted);
        String redisKey = null;
        String htmlStr = null;
        if (type == 0) {
            redisKey = "email-" + email;
            stringRedisTemplate.opsForValue().set(redisKey, hash);
            htmlStr = "안녕하세요 " + username + "님. 인증하기를 눌러주세요"
                    + "<a href='http://"+myIp+":8080" + "/auth/verify?useremail="+ email +"&key="+hash+"'>인증하기</a></p>";
        } else if (type == 1) {
            redisKey = "changepw-" + username;
            stringRedisTemplate.opsForValue().set(redisKey, hash);
            htmlStr ="안녕하세요 " + username + "님. 비밀번호 변경하를 눌러주세요"
                    + "<a href='http://"+myIp+":8080" + "/auth/vfpwemail?username="+ username +"&key="+hash+"'>비밀번호 변경하기</a></p>";
        }
        stringRedisTemplate.expire(redisKey, 10*24*1000, TimeUnit.MILLISECONDS); // for one day

        message.setText(htmlStr, "UTF-8", "html");
        javaMailSender.send(message);
    }

    public String getSHA512Token(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public String verifyEmail(String userEmail, String hash) {
        log.info("verify email 동작");
        if(stringRedisTemplate.opsForValue().get("email-"+userEmail).equals(hash)){
            ValueOperations<String, Object> memvop = memberRedisTemplate.opsForValue();
            Member member = (Member) memvop.get("toverify-"+userEmail);
            memberRepository.save(member);
            stringRedisTemplate.delete("email-"+userEmail);
            memberRedisTemplate.delete("toverify-"+userEmail);
        } else {
            return "error";
        }
        log.info("verify email 성공");


        return "이메일 인증 성공";
    }
}
