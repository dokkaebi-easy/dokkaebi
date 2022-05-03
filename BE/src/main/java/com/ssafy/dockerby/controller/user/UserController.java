package com.ssafy.dockerby.controller.user;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.user.SigninDto;
import com.ssafy.dockerby.dto.user.SignupDto;
import com.ssafy.dockerby.dto.user.UserDetailDto;
import com.ssafy.dockerby.dto.user.UserResponseDto;
import com.ssafy.dockerby.service.user.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
        log.info("signup API received ID: {}",signupDto.getPrincipal());

        UserResponseDto userResponseDto = userService.signup(signupDto);
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


    // security에서 로그아웃 성공시 Redirect하는 api
    @ApiIgnore//swagger에서 hidden 시키는 어노테이션
    @GetMapping("/signout/success")
    public ResponseEntity signoutSuccess() {
        log.info("Logout Successful");
        Map<String, Object> map = new HashMap<>();
        map.put("status", "Success");
        map.put("message", "Logout Successful");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @ApiOperation(value = "아이디 중복체크", notes = "사용 가능한 아이디는 true, 중복된 아이디는 false를 반환")
    @PostMapping("/duplicate/id")
    public ResponseEntity duplicatepPrincipal(@RequestParam String id)
        throws UserDefindedException {
        log.info("duplicatepPrincipal API received ID: {}",id);
        Map<String, Object> map = new HashMap<>();
        String state = userService.duplicatePrincipalCheck(id) ? "Success" : "Fail";

        map.put("state", state);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @ApiOperation(value = "이름 중복체크", notes = "사용 가능한 이름은 true, 중복된 이름은 false를 반환")
    @PostMapping("/duplicate/name")
    public ResponseEntity duplicateName(@RequestParam String name) throws UserDefindedException {
        log.info("duplicatepPrincipal API received name: {}",name);

        Map<String, Object> map = new HashMap<>();
        String state = userService.duplicateNameCheck(name) ? "Success" : "Fail";

        map.put("state", state);
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @PostMapping("/login/check")
    public ResponseEntity loginCheck(HttpServletRequest request) {
        //요청 로그출력
        HttpSession session = request.getSession();
        UserDetailDto userDetailDto = (UserDetailDto) session.getAttribute("user");
        log.info("session UserCheck Pricipal : {} ", userDetailDto.getUsername());
        Map<String, Object> map = new HashMap<>();

        map.put("name",  userDetailDto.getUsername());
        return new ResponseEntity(map, HttpStatus.OK);
    }

    // , security에서 로그인 성공시 Redirect하는 api
    @ApiIgnore //swagger에서 hidden 시키는 어노테이션
    @GetMapping("/signin/success")
    public ResponseEntity loginSuccess() {
        log.info("Login Successful");
        Map<String, Object> map = new HashMap<>();
        map.put("status", "Success");
        map.put("message", "Login Successful");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    // security에서 로그인 실패시 Redirect하는 api
    @ApiIgnore//swagger에서 hidden 시키는 어노테이션
    @GetMapping("/signin/fail")
    public ResponseEntity loginFail() {
        log.info("Login failed");
        Map<String, Object> map = new HashMap<>();
        map.put("status", "Fail");
        map.put("message", "Login failed");
        return new ResponseEntity(map, HttpStatus.OK);
    }

}
