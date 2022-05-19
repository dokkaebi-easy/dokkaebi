package com.ssafy.dockerby.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {
        log.info("logout Start");
        HttpSession session = request.getSession();
        //세션 무효화
        session.invalidate();
        log.info("logout Done");
    }
}