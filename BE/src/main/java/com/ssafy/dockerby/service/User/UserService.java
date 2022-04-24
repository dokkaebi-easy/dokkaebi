package com.ssafy.dockerby.service.User;


import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.User.SignupDto;
import com.ssafy.dockerby.dto.User.UserResponseDto;
import java.io.IOException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Boolean duplicatePrincipalCheck(String principal) throws UserDefindedException;
    Boolean duplicateNameCheck(String name) throws UserDefindedException;

    UserResponseDto signup(SignupDto signupDto) throws IOException, UserDefindedException;

}

