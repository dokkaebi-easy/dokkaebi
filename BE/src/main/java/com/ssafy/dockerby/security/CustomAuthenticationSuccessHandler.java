package com.ssafy.dockerby.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.dockerby.dto.user.UserDetailDto;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("Authentication Succss");

        UserDetailDto userDetailDto = (UserDetailDto) authentication.getPrincipal();

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600);//session 최대 유효시간(초) 설정. 1시간
        session.setAttribute("user",userDetailDto); // 세션에 user 정보 저장

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        Map<String, Object> map = new HashMap<>();
        map.put("status", "Success");
        map.put("message", "Login Successful");
        String result = objectMapper.writeValueAsString(map);

        response.getWriter().write(result);
    }

}
