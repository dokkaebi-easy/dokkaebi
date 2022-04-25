package com.ssafy.dockerby.service.user;

import com.ssafy.dockerby.common.ExceptionClass;
import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.user.SignupDto;
import com.ssafy.dockerby.dto.user.UserDetailDto;
import com.ssafy.dockerby.dto.user.UserResponseDto;
import com.ssafy.dockerby.entity.user.User;
import com.ssafy.dockerby.repository.user.UserRepository;
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
    public UserResponseDto signup(SignupDto signupDto) throws IOException, UserDefindedException {
        // 인증 확인
        isSignupValied(signupDto);
        log.info("Verification passed : {}", signupDto.getPrincipal());
        //Credential encode 변환 후 User객체 생성 //현재 "USER" roll만 입력, 추후 다른 권한 생길시 변경
        User user = User.of(signupDto, passwordEncoder.encode(signupDto.getCredential()));
        userRepository.save(user);
        log.info("User signup Complete : {}", user.getPrincipal());
        UserResponseDto userResponseDto = UserResponseDto.of(user);
        userResponseDto.SuccessState();
        return (userResponseDto);
    }

    //아이디 중복 검증
    @Override
    public Boolean duplicatePrincipalCheck(String principal) throws UserDefindedException {
        if (userRepository.findOneByPrincipal(principal).isPresent()) {
            return false;
        }
        return true;
    }

    //이름 중복 검증
    @Override
    public Boolean duplicateNameCheck(String name) throws UserDefindedException {
        if (userRepository.findOneByName(name).isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String pricipal) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByPrincipal(pricipal);
        if (!user.isPresent()) {
            log.error("Not Found User");
            throw new UsernameNotFoundException("Not Found User");
        }
        UserDetailDto userDetailDto = UserDetailDto.of(user.get());
        return userDetailDto;
    }

    //유효성 검증
    private void isSignupValied(SignupDto signupDto) throws UserDefindedException {
        log.info("start isValied : {}", signupDto.getPrincipal());
        //아이디 중복 검증

        if(!duplicatePrincipalCheck(signupDto.getPrincipal())) {
            log.error("This ID is already registered: {}", signupDto.getPrincipal());
            throw new UserDefindedException(ExceptionClass.USER, HttpStatus.BAD_REQUEST,
                "already registered ID");
        }
        //인증키 확인
        if (!signupDto.getAuthKey().equals(authKey)) {
            log.error("Authentication key mismatch");
            throw new UserDefindedException(ExceptionClass.USER, HttpStatus.BAD_REQUEST,
                "This is not a valid SecretKey");
        }

    }
}
