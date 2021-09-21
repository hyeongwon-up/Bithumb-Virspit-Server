package com.virspit.virspitauth.domain.member.service;

//import com.sun.jersey.api.MessageException;

import com.virspit.virspitauth.domain.member.dto.request.MemberSignInRequestDto;
import com.virspit.virspitauth.domain.member.dto.request.MemberSignUpRequestDto;
import com.virspit.virspitauth.domain.member.dto.response.MemberSignInResponseDto;
import com.virspit.virspitauth.domain.member.entity.Member;
import com.virspit.virspitauth.domain.member.repository.MemberRepository;
import com.virspit.virspitauth.jwt.JwtGenerator;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
    private final RedisTemplate<String, Integer> verifyRedisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final JavaMailSenderImpl javaMailSender;

    @Value("${my.ip}")
    private String myIp;

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

        memberRepository.save(member);

        return "회원가입 성공";
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

    public String verifyUserEmail(String userEmail) throws Exception {
        log.info("verify email 동작");
        int rand = new Random().nextInt(999999);
        verifyRedisTemplate.opsForValue().set("verify-"+userEmail, rand);

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

        verifyRedisTemplate.expire("verify-"+userEmail, 10*24*1000, TimeUnit.MILLISECONDS);
        // expire 하루

        return "이메일 인증 요청 성공";
    }


    public Boolean verifyNumber(String userEmail, Integer number) throws Exception {
        if(verifyRedisTemplate.opsForValue().get("verify-"+userEmail) != null) {
            int verifiedNumber = verifyRedisTemplate.opsForValue().get("verify-"+userEmail);
            if(verifiedNumber != number) {
                throw new Exception("인증번호가 틀렸습니다.");
            }
            verifyRedisTemplate.delete("verify-"+userEmail);
            return true;
        } else {
            throw  new Exception("인증이 필요한 이메일이 아닙니다.");
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
                        "<a href='http://" + myIp + ":8083" + "/auth/findpwd/res?useremail="+userEmail+"&key="+hash+"'>" +
                        "비밀번호 변경하기</a></p>" +
                        "<br>" +
                        "반드시 로그인 후 비밀번호를 변경해주세요.!";

        stringRedisTemplate.opsForValue().set("changepw-" + userEmail, hash);
        message.setText(content, "UTF-8", "html");
        javaMailSender.send(message);

        return true;
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

    public Boolean changePassword(String userEmail, String hash) throws Exception {
        log.info("changePassword 동작");
        if (stringRedisTemplate.opsForValue().get("changepw-" + userEmail).equals(hash)) {
            stringRedisTemplate.delete("changepw-" + userEmail);

            Member member = memberRepository.findByEmail(userEmail);
            member.setPassword(passwordEncoder.encode("virspit!23$"));
            memberRepository.save(member);

            return true;
        } else {
            throw new Exception("오류");
        }
    }
}
