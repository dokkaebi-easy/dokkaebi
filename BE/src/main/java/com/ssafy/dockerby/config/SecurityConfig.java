package com.ssafy.dockerby.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity//스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨, 스프링 시큐리티 필터가 SecurityConfig 이것을 말한다.
//지금부터 등록할 필터가 기본 필터에 등록이 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
            .csrf().disable()//csrf 토큰 비활성화 (테스트 시 걸어두는 게 좋음)
            .authorizeRequests()
            .antMatchers("/**").permitAll(); // 모든주소에 대한 접근 허용
    }

    //PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}