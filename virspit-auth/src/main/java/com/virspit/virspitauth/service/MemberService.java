package com.virspit.virspitauth.service;


import com.virspit.virspitauth.dto.model.Member;
import com.virspit.virspitauth.dto.request.InitPwdRequestDto;
import com.virspit.virspitauth.dto.request.MemberChangePwdRequestDto;
import com.virspit.virspitauth.dto.request.MemberSignInRequestDto;
import com.virspit.virspitauth.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.dto.response.MemberSignInResponseDto;
import com.virspit.virspitauth.dto.response.MemberInfoResponseDto;
import com.virspit.virspitauth.error.ErrorCode;
import com.virspit.virspitauth.error.exception.InvalidValueException;
import com.virspit.virspitauth.feign.MemberServiceFeignClient;
import com.virspit.virspitauth.jwt.JwtGenerator;
import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final RedisTemplate<String, Integer> verifyRedisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final JavaMailSenderImpl javaMailSender;
    private final MemberServiceFeignClient memberServiceFeignClient;

    @Value("${my.ip}")
    private String myIp;

    public MemberInfoResponseDto register(MemberSignUpRequestDto memberSignUpRequestDto) {
        String pwd = memberSignUpRequestDto.getPassword();
        memberSignUpRequestDto.setPassword(passwordEncoder.encode(pwd));


        return memberServiceFeignClient.save(memberSignUpRequestDto);
    }


    public MemberSignInResponseDto login(MemberSignInRequestDto memberSignInRequestDto) throws Exception {
        log.info("login 시도 email : " + memberSignInRequestDto.getEmail());
        final String userEmail = memberSignInRequestDto.getEmail();

        //블랙리스트 검증
        if (stringRedisTemplate.opsForValue().get("email-" + userEmail) != null) {
            throw new InvalidValueException(userEmail, ErrorCode.BLACKLIST_MEMBER);
        }


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, memberSignInRequestDto.getPassword()));
        log.info("인증 성공");

        Member member = memberServiceFeignClient.findByEmail(userEmail);
        log.info("login member : " + member.toString());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userEmail);
        final String accessToken = jwtGenerator.generateAccessToken(userDetails);
        final String refreshToken = jwtGenerator.generateRefreshToken(userEmail);


        //generate Token and save in redis
        stringRedisTemplate.opsForValue().set("refresh-" + userEmail, refreshToken);

        log.info("login 성공 email : " + userEmail);
        return new MemberSignInResponseDto(MemberInfoResponseDto.of(member), accessToken, refreshToken);
    }

    public String verifyUserEmail(String userEmail) throws Exception {

        if (memberServiceFeignClient.checkByEmail(userEmail) == false) {
            log.warn("이미 가입한 이메일 입니다.");
            throw new InvalidValueException(ErrorCode.EMAIL_ALREADY_EXIST);
        }

        log.info("verify email 동작");
        int rand = new Random().nextInt(999999);
        verifyRedisTemplate.opsForValue().set("verify-" + userEmail, rand);

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
        message.setSubject("[본인인증] VIRSPIT 이메일 인증");

        String content =
                "VIRSPIT을 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + rand + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";

        message.setText(content, "UTF-8", "html");
        javaMailSender.send(message);

        verifyRedisTemplate.expire("verify-" + userEmail, 10 * 24 * 1000, TimeUnit.MILLISECONDS);
        // expire 하루

        return "이메일 인증 요청 성공";
    }


    public Boolean verifyNumber(String userEmail, Integer number) throws Exception {
        if (verifyRedisTemplate.opsForValue().get("verify-" + userEmail) != null) {
            int verifiedNumber = verifyRedisTemplate.opsForValue().get("verify-" + userEmail);
            if (verifiedNumber != number) {
                throw new Exception("인증번호가 틀렸습니다.");
            }
            verifyRedisTemplate.delete("verify-" + userEmail);
            return true;
        } else {
            throw new Exception("인증이 필요한 이메일이 아닙니다.");
        }

    }

    public Boolean findPasssword(String userEmail) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
        message.setSubject("[본인인증] VIRSPIT 비밀번호 초기화");

        int rand = new Random().nextInt(999999);
        String formatted = String.format("%06d", rand);
        String hash = getSHA512Token(userEmail, formatted);
        String content =
                "VIRSPIT 비밀번호 초기화" +
                        "<br><br>" +
                        "초기화 비밀번호는 virspit!23$ 입니다." +
                        "<br>" +
                        "초기화를 원하시면 아래 링크를 눌러주세요." +
                        "<br>" +
                        "<a href='http://" + myIp + ":8083" + "/auth/findpwd/res?useremail=" + userEmail + "&key=" + hash + "'>" +
                        "비밀번호 변경하기</a></p>" +
                        "<br>" +
                        "반드시 로그인 후 비밀번호를 변경해주세요.!";

        stringRedisTemplate.opsForValue().set("changepw-" + userEmail, hash);
        message.setText(content, "UTF-8", "html");
        javaMailSender.send(message);

        return true;
    }

    public String getSHA512Token(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public Boolean initPassword(String userEmail, String hash) throws Exception {
        log.info("changePassword 동작");
        if (stringRedisTemplate.opsForValue().get("changepw-" + userEmail).equals(hash)) {
            stringRedisTemplate.delete("changepw-" + userEmail);

            String initPwd = passwordEncoder.encode("virspit!23$");
            InitPwdRequestDto initPwdRequestDto = new InitPwdRequestDto(userEmail, initPwd);

            memberServiceFeignClient.initPwd(initPwdRequestDto);

            return true;
        } else {
            throw new Exception("오류");
        }
    }

    public Boolean changePassword(MemberChangePwdRequestDto memberChangePwdRequestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(memberChangePwdRequestDto.getEmail(), memberChangePwdRequestDto.getBeforePwd()));

        String password = passwordEncoder.encode(memberChangePwdRequestDto.getAfterPwd());
        InitPwdRequestDto initPwdRequestDto = new InitPwdRequestDto(memberChangePwdRequestDto.getEmail(), password);
        memberServiceFeignClient.initPwd(initPwdRequestDto);
        return true;
    }

    public String logout(String accessToken) {
        String memberName = null;

        try {
            memberName = jwtGenerator.getUsernameFromToken(accessToken);
        } catch (ExpiredJwtException e) {
            memberName = e.getClaims().getSubject();
            log.info("already logout : " + memberName);
        }

        stringRedisTemplate.delete("refresh-" + memberName);
        stringRedisTemplate.opsForValue().set(accessToken, "true");
        stringRedisTemplate.expire(accessToken, 10 * 6 * 1000, TimeUnit.MILLISECONDS);

        return "logout success";
    }

}
