package com.ssafy.dockerby.service;

import com.ssafy.dockerby.common.ExceptionClass;
import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.User.SigninRequestDto;
import com.ssafy.dockerby.dto.User.UserDto;
import com.ssafy.dockerby.dto.User.UserResponseDto;
import com.ssafy.dockerby.entity.User.User;
import com.ssafy.dockerby.repository.UserRepository;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    //임시 authKey
    String authKey = "authKey";

    @Override
    public UserResponseDto signup(UserDto userDto) throws IOException, UserDefindedException {
        // 인증 확인
        isValied(userDto);
        log.info("Verification passed : {}", userDto.getPrincipal());
        //Credential encode 변환 후 User객체 생성
        User user = User.of(userDto, passwordEncoder.encode(userDto.getCredential()));
        userRepository.save(user);
        log.info("User signup Complete : {}", user.getPrincipal());
        return (UserResponseDto.of(user));
    }

    @Override
    public UserResponseDto signin(SigninRequestDto signinRequestDto)
        throws IOException, UserDefindedException {
        Optional<User> user = userRepository.findByPrincipal(signinRequestDto.getPrincipal());
        //검색 결과 여부 확인
        if (!user.isPresent()) {
            log.error("Principal does not exist : {}", signinRequestDto.getPrincipal());
            throw new UserDefindedException(ExceptionClass.USER, HttpStatus.BAD_REQUEST,
                "Principal does not exist");
        }
        //password 일치여부 확인
        else if (!passwordEncoder.matches(signinRequestDto.getCredential(),
            user.get().getCredential())) {
            log.error("Passwords do not match");
            throw new UserDefindedException(ExceptionClass.USER, HttpStatus.BAD_REQUEST,
                "Passwords do not match");
        } else {
            log.info("Login Successful : {}", user.get().getPrincipal());
            return UserResponseDto.of(user.get());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public void duplicatePrincipalCheck(String principal) throws UserDefindedException {
        //아이디 중복 검증
        if (userRepository.findOneByPrincipal(principal).isPresent()) {
            log.error("This ID is already registered: {}", principal);
            throw new UserDefindedException(ExceptionClass.USER, HttpStatus.BAD_REQUEST,
                "already registered ID");
        }
    }

    //유효성 검증
    private void isValied(UserDto userDto) throws UserDefindedException {
        log.info("start isValied : {}", userDto.getPrincipal());
        //아이디 중복 검증
        duplicatePrincipalCheck(userDto.getPrincipal());
        //인증키 확인
        if (!userDto.getAuthKey().equals(authKey)) {
            log.error("Authentication key mismatch {}", userDto.getAuthKey());
            throw new UserDefindedException(ExceptionClass.USER, HttpStatus.BAD_REQUEST,
                "This is not a valid SecretKey");
        }

    }
}
