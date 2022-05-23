package com.ssafy.dockerby.config;

import com.ssafy.dockerby.dto.user.UserDetailDto;
import com.ssafy.dockerby.security.CustomAuthenticationFailureHandler;
import com.ssafy.dockerby.security.CustomAuthenticationProvider;
import com.ssafy.dockerby.security.CustomAuthenticationSuccessHandler;
import com.ssafy.dockerby.security.CustomLogoutHandler;
import com.ssafy.dockerby.security.CustomLogoutSuccessHandler;
import com.ssafy.dockerby.security.CustomUsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
            .cors().configurationSource(corsConfigurationSource())
        ;
        http
            .authorizeRequests()
            .requestMatchers(request -> CorsUtils.isPreFlightRequest(request)).permitAll()
            .antMatchers("/api/user/**", "/swagger-ui.html/**", "/configuration/**",
                "/swagger-resources/**", "/v2/api-docs", "/webjars/**",
                "/webjars/springfox-swagger-ui/*.{js,css}").permitAll() // 모두 허용
            .antMatchers("/api/project/hook/**").permitAll()
            .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().authenticated()
;
        //로그인 기능
        http.formLogin().disable();
        // 새로구현한 Filter를 UsernamePasswordAuthenticationFilter layer에 삽입
        http.addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//        //자동 로그인 기능
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
//                ,"remember-me"
            ) // 로그아웃 후 해당 쿠키 삭제
            .addLogoutHandler(customLogoutHandler) // 로그아웃 처리 핸들러
            .logoutSuccessHandler(customLogoutSuccessHandler) // 로그아웃 성공시 핸들러
        ;
        http
            .sessionManagement()
            .maximumSessions(1)//최대 허용 가능 세션 수 설정 , -1은 무제한 허용
            .maxSessionsPreventsLogin(true)// 동시로그인 설정
            .expiredUrl("/api/user/login/check")

        ;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    // js, css, image 설정은 보안 설정의 영향 밖에 있도록 만들어주는 설정.
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
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

    protected CustomUsernamePasswordAuthenticationFilter getAuthenticationFilter() {
        CustomUsernamePasswordAuthenticationFilter authFilter = new CustomUsernamePasswordAuthenticationFilter();
        try {
            authFilter.setFilterProcessesUrl("/api/user/auth/signin");
            authFilter.setAuthenticationManager(this.authenticationManagerBean());
            authFilter.setUsernameParameter("principal");
            authFilter.setPasswordParameter("credential");
            authFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
            authFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authFilter;
    }

}