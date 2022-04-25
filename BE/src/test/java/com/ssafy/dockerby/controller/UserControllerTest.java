//package com.ssafy.dockerby.controller;
//
//import com.ssafy.dockerby.dto.User.SigninDto;
//import com.ssafy.dockerby.dto.User.UserDto;
//import com.ssafy.dockerby.dto.User.UserResponseDto;
//import java.io.FileNotFoundException;
//import java.util.concurrent.ExecutionException;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//class UserControllerTest {
//
//
//    @Autowired
//    UserController userController;
//
//    @Test
//    public void 회원가입()
//        throws Exception, ExecutionException, InterruptedException, FileNotFoundException {
//        // given
//        UserDto userDto = UserDto.builder()
//            .principal("아이디")
//            .credential("비밀번호")
//            .name("김민현")
//            .authKey("authKey")
//            .build();
//        // when
//        ResponseEntity<UserResponseDto> result1 = userController.signup(userDto);
//        // then
//        Assertions.assertThat(result1.getBody().getPrincipal()).isEqualTo(userDto.getPrincipal());
//        Assertions.assertThat(result1.getBody().getName()).isEqualTo(userDto.getName());
//
//    }
//
//    @Test
//    public void 회원가입및로그인()
//        throws Exception, ExecutionException, InterruptedException, FileNotFoundException {
//        // given
//        UserDto userDto = UserDto.builder()
//            .principal("아이디")
//            .credential("비밀번호")
//            .name("김민현")
//            .authKey("authKey")
//            .build();
//        ResponseEntity<UserResponseDto> result1 = userController.signup(userDto);
//
//        SigninDto signinRequestDto = SigninDto
//            .builder()
//            .principal(userDto.getPrincipal())
//            .credential(userDto.getCredential())
//            .build();
//        // when
////        ResponseEntity<UserResponseDto> result2 = userController.signin(signinRequestDto);
////
////        // then
////        Assertions.assertThat(result1.getBody().getPrincipal())
////            .isEqualTo(result2.getBody().getPrincipal());
////        Assertions.assertThat(result1.getBody().getName()).isEqualTo(result2.getBody().getName());
//
//    }
//
//}