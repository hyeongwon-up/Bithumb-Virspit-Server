package com.virspit.virspitauth.domain.user.controller;

import com.hwhy.moommoo.domain.user.dto.request.UserSignInRequestDto;
import com.hwhy.moommoo.domain.user.dto.request.UserSignUpRequestDto;
import com.hwhy.moommoo.domain.user.dto.response.UserSignInResponseDto;
import com.hwhy.moommoo.domain.user.repository.UserRepository;
import com.hwhy.moommoo.domain.user.service.UserServiceImpl;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = "users") @RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @PostMapping("/signup")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value={
            @ApiResponse(code=400, message = "something wrong"),
            @ApiResponse(code=403, message = "승인거절"),
            @ApiResponse(code=422, message = "중복된 username")})
    public ResponseEntity<String> signup(@ApiParam("Signup User")
                                         @RequestBody UserSignUpRequestDto userDto){
        log.info("################## 회원가입 controller Start ###########");
        userService.signup(userDto);
        return ResponseEntity.ok("회원가입 완료");
    }
    @PostMapping("/signin")
    @ApiOperation(value = "${UserController.signin}")
    @ApiResponses(value={
            @ApiResponse(code=400, message = "something wrong"),
            @ApiResponse(code=422, message = "유효하지 않은 아이디 / 비밀번호")})
    public ResponseEntity<UserSignInResponseDto> signin(@ApiParam("Signin User")
                                         @RequestBody UserSignInRequestDto userDto){
        log.info("################## 로그인 controller Start ###########");
        return ResponseEntity.ok(userService.signin(userDto));
    }



    @GetMapping("/test")
    public String test(String email){

        if(userRepository.findByEmail(email).isPresent()) {
            return "ok";
        }
        else{
            return "fail";
        }
    }



}
