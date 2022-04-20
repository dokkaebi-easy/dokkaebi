package com.ssafy.dockerby.controller;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.UserDto;
import com.ssafy.dockerby.service.UserService;
import io.swagger.annotations.Api;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody UserDto userDto) throws IOException, UserDefindedException {
        userService.signUp(userDto);
        return userDto;
    }
    @PostMapping("/logIn")
    public UserDto logIn(UserDto userDto) throws IOException, UserDefindedException {
        return userService.logIn(userDto);
    }
}
