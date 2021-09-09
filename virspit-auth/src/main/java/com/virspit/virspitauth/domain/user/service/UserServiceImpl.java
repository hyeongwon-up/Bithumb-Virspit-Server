package com.virspit.virspitauth.domain.user.service;

import com.hwhy.moommoo.domain.user.dto.request.UserSignInRequestDto;
import com.hwhy.moommoo.domain.user.dto.request.UserSignUpRequestDto;
import com.hwhy.moommoo.domain.user.dto.response.UserSignInResponseDto;
import com.hwhy.moommoo.domain.user.entity.Role;
import com.hwhy.moommoo.domain.user.entity.User;
import com.hwhy.moommoo.domain.user.repository.UserRepository;
import com.hwhy.moommoo.security.domain.SecurityProvider;
import com.hwhy.moommoo.security.exception.SecurityRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityProvider provider;
    private final ModelMapper modelMapper;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsById(long id) {
        return userRepository.existsById(id);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public String signup(UserSignUpRequestDto userSignUpRequestDto) {
        log.info("################## 회원가입 Service Start ###########");
        if(!userRepository.existsByUsername(userSignUpRequestDto.getEmail())){



            User user = User.builder()
                    .username(userSignUpRequestDto.getName())
                    .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                    .email(userSignUpRequestDto.getEmail())
                    .gender(userSignUpRequestDto.getGender())
                    .birthdayDate(userSignUpRequestDto.getBirthdayDate())
                    .build();


            List<Role> list = new ArrayList<>();
            list.add(Role.USER);
            user.setRoles(list);
            userRepository.save(user);
            return provider.createToken(user.getUsername(), user.getRoles());
        }else{
            throw new SecurityRuntimeException("중복된 ID 입니다", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public UserSignInResponseDto signin(UserSignInRequestDto userSignInRequestDto) {
        log.info("################## 로그인 Service Start ###########");
        log.info("3"+userRepository.findByEmail(userSignInRequestDto.getEmail()));

        try{
            String token = (passwordEncoder.matches(
                    userSignInRequestDto.getPassword(),
                    userRepository.findByEmail(userSignInRequestDto.getEmail()).get().getPassword()))
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
