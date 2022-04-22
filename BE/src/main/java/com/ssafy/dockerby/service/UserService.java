package com.ssafy.dockerby.service;


import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.User.SigninRequestDto;
import com.ssafy.dockerby.dto.User.UserDto;
import com.ssafy.dockerby.dto.User.UserResponseDto;
import java.io.IOException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void duplicatePrincipalCheck(String principal) throws UserDefindedException;

    UserResponseDto signup(UserDto userDto) throws IOException, UserDefindedException;

    UserResponseDto signin(SigninRequestDto userRequestDto)
        throws IOException, UserDefindedException;

}

