package com.ssafy.dockerby.controller;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.User.SigninRequestDto;
import com.ssafy.dockerby.dto.User.UserDto;
import com.ssafy.dockerby.dto.User.UserResponseDto;
import com.ssafy.dockerby.service.UserService;
import io.swagger.annotations.Api;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"User"}) //Swagger 중간 제목
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserDto userDto) throws IOException, UserDefindedException {
        UserResponseDto userResponseDto = userService.signup(userDto);
        return ResponseEntity.ok(userResponseDto);
    }
    @PostMapping("/signin")
    public ResponseEntity<UserResponseDto> signin(@RequestBody SigninRequestDto userRequestDto) throws IOException, UserDefindedException {
        UserResponseDto userResponseDto = userService.signin(userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }


}
