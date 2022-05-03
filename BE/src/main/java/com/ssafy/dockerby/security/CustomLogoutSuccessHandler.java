package com.ssafy.dockerby.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        log.info("onLogoutSuccess Start : authentication = {} ",authentication);
        if (authentication != null && authentication.getDetails() != null) {
            try {
                request.getSession().invalidate();
                log.info("onLogoutSuccess : logout Success");
            } catch (Exception e) {
                log.error("onLogoutSuccess : logout Failed : {}",e);
            }
        }
        log.info("onLogoutSuccess Done");
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/api/user/signout/success");
    }
}