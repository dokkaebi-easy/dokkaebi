package com.dokkaebi.controller.user;

import com.dokkaebi.common.exception.UserDefindedException;
import com.dokkaebi.dto.user.SigninDto;
import com.dokkaebi.dto.user.SignupDto;
import com.dokkaebi.dto.user.UserResponseDto;
import com.dokkaebi.service.user.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"User"}) //Swagger 중간 제목
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 한다")
    @PostMapping( "/signup")
    public ResponseEntity<UserResponseDto> signup(@Valid @RequestBody SignupDto signupDto)
        throws IOException, UserDefindedException {
        log.info("API Request received : received ID = {}",signupDto.getPrincipal());

        UserResponseDto userResponseDto = userService.signup(signupDto);

        log.info("API Response return : Response = {}",userResponseDto.toString());
        return ResponseEntity.ok(userResponseDto);
    }

    @ApiOperation(value = "로그인", notes = "데이터 형식 : json")
    @PostMapping("/auth/signin")
    public void signin(@RequestBody SigninDto signinDto) {
    }


    //swagger API 생성용// Security에서 로그아웃 처리함
    @ApiOperation(value = "로그아웃", notes = "로그아웃을 한다")
    @PostMapping("/auth/signout")
    public void signout() {
    }


    @ApiOperation(value = "아이디 중복체크", notes = "사용 가능한 아이디는 true, 중복된 아이디는 false를 반환")
    @PostMapping("/duplicate/id")
    public ResponseEntity duplicatePrincipal(@RequestParam String id)
        throws UserDefindedException {
        log.info("API Request received : received Id = {}",id);
        Map<String, Object> map = new HashMap<>();
        String state = userService.duplicatePrincipalCheck(id) ? "Success" : "Fail";

        map.put("state", state);

        log.info("API Response return : Response = {}",state);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @ApiOperation(value = "이름 중복체크", notes = "사용 가능한 이름은 true, 중복된 이름은 false를 반환")
    @PostMapping("/duplicate/name")
    public ResponseEntity duplicateName(@RequestParam String name) throws UserDefindedException {
        log.info("API Request received : received name = {}",name);

        Map<String, Object> map = new HashMap<>();
        String state = userService.duplicateNameCheck(name) ? "Success" : "Fail";

        map.put("state", state);

        log.info("API Response return : Response = {}",state);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @ApiOperation(value = "이름 중복체크", notes = "사용 가능한 이름은 true, 중복된 이름은 false를 반환")
    @PostMapping("/validate/authKey")
    public ResponseEntity validateAuthKey(@RequestParam String authKey) throws UserDefindedException {
        log.info("API Request received : received authKey = {}",authKey);

        Map<String, Object> map = new HashMap<>();
        String state = userService.validateAuthKeyCheck(authKey) ? "Success" : "Fail";

        map.put("state", state);

        log.info("API Response return : Response = {}",state);
        return new ResponseEntity(map, HttpStatus.OK);
    }

}
