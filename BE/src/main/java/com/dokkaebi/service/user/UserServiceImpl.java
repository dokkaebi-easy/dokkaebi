package com.dokkaebi.service.user;

import com.dokkaebi.repository.user.UserRepository;
import com.dokkaebi.common.ExceptionClass;
import com.dokkaebi.common.exception.UserDefindedException;
import com.dokkaebi.dto.user.SigninDto;
import com.dokkaebi.dto.user.SignupDto;
import com.dokkaebi.dto.user.UserDetailDto;
import com.dokkaebi.dto.user.UserResponseDto;
import com.dokkaebi.entity.user.User;
import com.dokkaebi.util.FileManager;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
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
    private String authKey;

    @Autowired
    public void UserServiceImpl() throws IOException {
        this.authKey = FileManager.loadFile("/home/conf", "AuthKey");
    }

    @Override
    public UserResponseDto signup(SignupDto signupDto) throws UserDefindedException, IOException {
        log.info("signup Start : userName = {} ",signupDto.getName());
        // 인증 확인
        isSignupValidate(signupDto);
        //Credential encode 변환 후 User객체 생성 //현재 "USER" roll만 입력, 추후 다른 권한 생길시 변경
        User user = User.of(signupDto, passwordEncoder.encode(signupDto.getCredential()));
        userRepository.save(user);
        log.info("User signup Success : userName = {}",user.getName());

        UserResponseDto userResponseDto = UserResponseDto.of(user);
        userResponseDto.SuccessState();

        log.info("signup Done : userName = {} ",user.getName());
        return (userResponseDto);
    }

    public UserDetailDto signin(SigninDto signinDto) {
        UserDetailDto userDetailDto = loadUserByUsername(signinDto.getPrincipal());

        String reqPassword = signinDto.getCredential();

        if (!passwordEncoder.matches(reqPassword, userDetailDto.getPassword())) {
            log.error("signin Failed : Passwords do not match");
            throw new BadCredentialsException("Passwords do not match\n");
        }
        log.info("signin Done : userName = {}",userDetailDto.getUsername());
        return userDetailDto;
    }

    //아이디 중복 검증
    @Override
    public Boolean duplicatePrincipalCheck(String principal) throws UserDefindedException {
        log.info("duplicatePrincipalCheck Start : received id = {}",principal);
        if (userRepository.findOneByPrincipal(principal).isPresent()) {
            log.info("duplicatePrincipalCheck False");
            return false;
        }
        log.info("duplicatePrincipalCheck True");
        return true;
    }

    //이름 중복 검증
    @Override
    public Boolean duplicateNameCheck(String name) throws UserDefindedException {
        log.info("duplicateNameCheck Start : received name = {}",name);
        if (userRepository.findOneByName(name).isPresent()) {
            log.info("duplicateNameCheck False");
            return false;
        }
        log.info("duplicateNameCheck True");
        return true;
    }

    @Override
    public Boolean validateAuthKeyCheck(String key){
        log.info("validateAuthKeyCheck Start : received authKey = {}",key);
        if(!key.equals(authKey)){
            log.info("validateAuthKeyCheck False");
            return false;
        }
        log.info("validateAuthKeyCheck True");
        return true;
    }

    @Override
    public UserDetailDto loadUserByUsername(String pricipal) throws UsernameNotFoundException {
        log.info("loadUserByUsername Start : received name = {}",pricipal);
        Optional<User> user = userRepository.findByPrincipal(pricipal);
        if (!user.isPresent()) {
            log.error("loadUserByUsername Failed : user Not Found");
            throw new UsernameNotFoundException("Not Found User");
        }
        log.info("loadUserByUsername Success");
        UserDetailDto userDetailDto = UserDetailDto.of(user.get());
        log.info("loadUserByUsername Done : response dto.name = {}",userDetailDto.getUsername());
        return userDetailDto;
    }

    //유효성 검증
    private void isSignupValidate(SignupDto signupDto) throws UserDefindedException, IOException {
        log.info("isSignupValidate Start : received name = {}",signupDto.getName());
        //아이디 중복 검증
        if (!duplicatePrincipalCheck(signupDto.getPrincipal())) {
            log.error("isSignupValidate Failed : This ID is already registered: {}", signupDto.getPrincipal());
            throw new UserDefindedException(ExceptionClass.USER, HttpStatus.BAD_REQUEST,
                "already registered ID");
        }

        // 비밀번호 정규식 검증
        if(!Pattern.matches("^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W))(?=.*[!@#$%^*+=-]).{8,16}$",signupDto.getCredential())){
            log.error("isSignupValidate Failed: Credential mismatch");
            throw new UserDefindedException(ExceptionClass.USER, HttpStatus.BAD_REQUEST,
              "Credential validation mismatch");
        }

        //인증키 확인
        if (!signupDto.getAuthKey().equals(authKey)) {
            log.error("isSignupValidate Failed: Authentication key mismatch");
            throw new UserDefindedException(ExceptionClass.USER, HttpStatus.BAD_REQUEST,
                "This is not a valid SecretKey");
        }
        log.info("isSignupValidate Success : Verification passed : {}", signupDto.getPrincipal());
    }
}
