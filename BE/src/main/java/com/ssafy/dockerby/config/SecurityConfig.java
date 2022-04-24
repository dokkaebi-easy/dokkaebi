package com.ssafy.dockerby.config;

import com.ssafy.dockerby.security.CustomAuthenticationFailureHandler;
import com.ssafy.dockerby.security.CustomAuthenticationProvider;
import com.ssafy.dockerby.security.CustomAuthenticationSuccessHandler;
import com.ssafy.dockerby.security.CustomLogoutHandler;
import com.ssafy.dockerby.security.CustomLogoutSuccessHandler;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨, 스프링 시큐리티 필터가 SecurityConfig 이것을 말한다.지금부터 등록할 필터가 기본 필터에 등록이 된다.
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
        ;

        http
            .authorizeRequests()
            //user 관련된 권한(로그인,로그아웃,회원가입) 및 swagger 모두 접근허용
            .antMatchers("/api/user/**", "/swagger-ui.html/**", "/configuration/**",
                "/swagger-resources/**", "/v2/api-docs", "/webjars/**",
                "/webjars/springfox-swagger-ui/*.{js,css}").permitAll()
            //그외 USER 권한만 허용
            .anyRequest().hasAnyRole("USER")
        ;
        //로그인 form
        http
            .formLogin()
            .loginProcessingUrl("/api/user/auth/signin") //로그인 요청
            .usernameParameter("principal") //username parameter 이름 변경
            .passwordParameter("credential")//password parameter 이름 변경
            .successHandler(
                customAuthenticationSuccessHandler) //  로그인 성공시 customAuthenticationSuccessHandler  사용
            .failureHandler(
                customAuthenticationFailureHandler) // 로그인 실패시 customAuthenticationFailureHandler 사용
        ;
        //자동 로그인 기능
//        http.rememberMe()
//            .rememberMeParameter("remember")      // 기본 파라미터명은 remember-me
//            .tokenValiditySeconds(3600)           // Default 는 14일
//            .alwaysRemember(true)                // 리멤버 미 기능이 활성화되지 않아도 항상 실행
//        ;
        // 로그아웃 처리
        http.logout()
            .logoutUrl("/api/user/auth/signout") // 로그아웃 처리 URL
            .logoutSuccessUrl("/api/user/auth/signin")
            .deleteCookies("JSESSIONID"
//              , "remember-me"
            ) // 로그아웃 후 해당 쿠키 삭제
            .addLogoutHandler(customLogoutHandler) // 로그아웃 처리 핸들러
            .logoutSuccessHandler(customLogoutSuccessHandler) // 로그아웃 성공시 핸들러
        ;
        http
            .sessionManagement()
            .maximumSessions(-1)//최대 허용 가능 세션 수 설정 , -1은 무제한 허용
            .maxSessionsPreventsLogin(true)// 동시로그인 설정
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

}