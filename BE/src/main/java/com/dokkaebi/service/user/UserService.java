package com.dokkaebi.service.user;


import com.dokkaebi.common.exception.UserDefindedException;
import com.dokkaebi.dto.user.SigninDto;
import com.dokkaebi.dto.user.SignupDto;
import com.dokkaebi.dto.user.UserDetailDto;
import com.dokkaebi.dto.user.UserResponseDto;
import java.io.IOException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

  @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Boolean duplicatePrincipalCheck(String principal) throws UserDefindedException;

    Boolean duplicateNameCheck(String name) throws UserDefindedException;

    Boolean validateAuthKeyCheck(String key);

    UserResponseDto signup(SignupDto signupDto) throws IOException, UserDefindedException;
    UserDetailDto signin(SigninDto signinDto) ;
}

