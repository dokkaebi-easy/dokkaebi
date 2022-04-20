package com.ssafy.dockerby.controller;

import com.ssafy.dockerby.common.Constants.ExceptionClass;
import com.ssafy.dockerby.common.exception.AroundHubException;
import com.ssafy.dockerby.dto.UserDto;
import com.ssafy.dockerby.service.UserService;
import io.swagger.annotations.Api;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    public UserDto signUp(@RequestBody UserDto userDto) throws IOException, AroundHubException {
        userService.signUp(userDto);
        return userDto;
    }
    @PostMapping("/logIn")
    public UserDto logIn(UserDto userDto) throws IOException, AroundHubException {
        return userService.logIn(userDto);
    }
}
