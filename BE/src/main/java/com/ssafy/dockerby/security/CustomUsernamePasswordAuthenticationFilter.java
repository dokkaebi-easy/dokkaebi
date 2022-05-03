package com.ssafy.dockerby.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.dockerby.dto.user.SigninDto;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;

public class CustomUsernamePasswordAuthenticationFilter extends
    UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken;
        if (request.getContentType().equals(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            // json request
            try {
                // read request body and mapping to login dto class by object mapper
                SigninDto signinDto = objectMapper.readValue(
                    request.getReader().lines().collect(Collectors.joining()), SigninDto.class);
                authenticationToken = new UsernamePasswordAuthenticationToken(
                    signinDto.getPrincipal(), signinDto.getCredential());
            } catch (IOException e) {
                e.printStackTrace();
                throw new AuthenticationServiceException("Request Content-Type(application/json) Parsing Error");
            }
        } else {
            // form-request
            String username = obtainUsername(request);
            String password = obtainPassword(request);
            authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        }
        this.setDetails(request, authenticationToken);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

}