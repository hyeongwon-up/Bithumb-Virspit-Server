package com.virspit.virspitauth.domain.user.service;



import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserService {
    List<User> findAll();
    Optional<User> findById(long id);
    void save(User user);
    boolean existsById(long id);
    long count();
    void deleteById(long id);
    String signup(UserSignUpRequestDto userSignUpRequestDto);
    UserSignInResponseDto signin(UserSignInRequestDto userSignInRequestDto);

}
