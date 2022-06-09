package com.dokkaebi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
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
    private ObjectMapper objectMapper = new ObjectMapper();

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
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        Map<String, Object> map = new HashMap<>();
        map.put("status", "Success");
        map.put("message", "Logout Successful");

        // JSON 형태로 변환하기
        // {"username" : "won", "age" : 20}
        String result = objectMapper.writeValueAsString(map);

        response.getWriter().write(result);
    }
}