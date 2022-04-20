package com.ssafy.dockerby.service;

import com.ssafy.dockerby.common.Constants.ExceptionClass;
import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.UserDto;
import com.ssafy.dockerby.entity.User;
import com.ssafy.dockerby.repository.UserRepository;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    String userFileName = "userinfo";

    public void signUp(UserDto userDto) throws IOException, UserDefindedException {
        if (userRepository.findByPrincipal(userDto.getPrincipal()).isPresent()){
            log.error("중복된 아이디");
            throw new UserDefindedException(ExceptionClass.USER, HttpStatus.BAD_REQUEST,"이미 가입된 아이디입니다");
        }
        userRepository.save(User.from(userDto));
    }

    public UserDto logIn(UserDto userDto) throws IOException, UserDefindedException {
        Optional<User> user = userRepository.findById(userDto.getId());
        if (user.get().equals(userDto)){
            return userDto;
        }else{
            throw new UserDefindedException(ExceptionClass.USER, HttpStatus.BAD_REQUEST,"비밀번호 혹은 아이디가 일치 하지 않습니다");
        }
    }

}
