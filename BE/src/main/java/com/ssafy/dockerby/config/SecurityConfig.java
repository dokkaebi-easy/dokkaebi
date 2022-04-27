package com.ssafy.dockerby.config;

import com.ssafy.dockerby.security.CustomLogoutHandler;
import com.ssafy.dockerby.security.CustomLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
//스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨, 스프링 시큐리티 필터가 SecurityConfig 이것을 말한다.지금부터 등록할 필터가 기본 필터에 등록이 된다.
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomLogoutHandler customLogoutHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors().configurationSource(corsConfigurationSource())
        ;
        http
            .authorizeRequests()
            .requestMatchers(request -> CorsUtils.isPreFlightRequest(request)).permitAll()
            .antMatchers("/api/user/**", "/swagger-ui.html/**", "/configuration/**",
                "/swagger-resources/**", "/v2/api-docs", "/webjars/**",
                "/webjars/springfox-swagger-ui/*.{js,css}").permitAll()
            .anyRequest().permitAll();

        //로그인 form
        http
            .formLogin().disable();

        // 로그아웃 처리
        http.logout()
            .logoutUrl("/api/user/auth/signout") // 로그아웃 처리 URL
            .logoutSuccessUrl("/api/user/auth/signin")
            .deleteCookies("JSESSIONID") // 로그아웃 후 해당 쿠키 삭제
            .addLogoutHandler(customLogoutHandler) // 로그아웃 처리 핸들러
            .logoutSuccessHandler(customLogoutSuccessHandler) // 로그아웃 성공시 핸들러
        ;
        http
            .sessionManagement()
            .maximumSessions(-1)//최대 허용 가능 세션 수 설정 , -1은 무제한 허용
            .maxSessionsPreventsLogin(true)// 동시로그인 설정
        ;
    }
    // CORS 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}