package com.ssafy.dockerby.security;

import com.ssafy.dockerby.dto.user.UserDetailDto;
import com.ssafy.dockerby.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        UserDetailDto user = (UserDetailDto) userService.loadUserByUsername(
            authentication.getName());

        String reqPassword = authentication.getCredentials().toString();

        if (!passwordEncoder.matches(reqPassword, user.getPassword())) {
            log.error("Passwords do not match");
            throw new BadCredentialsException("Passwords do not match\n");
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }


}
