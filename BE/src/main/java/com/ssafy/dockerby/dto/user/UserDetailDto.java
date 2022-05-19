package com.ssafy.dockerby.dto.user;

import com.ssafy.dockerby.entity.user.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Slf4j
public class UserDetailDto implements UserDetails {

    private Long id;
    private String name;
    private String role;
    private String principal;
    private String credential;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        //Spring Security의 권한정보를 담을때 ROLE가 반드시 앞에 붙어야 함
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return credential;
    }

    @Override
    public String getUsername() {
        return principal;
    }

    // 계정 만료 여부(true: 만료되지 않음, false: 만료됨)
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    // 계정 잠금 여부(true: 계정잠금아님, false: 계정잠금상태)
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    // 계정 패스워드 만료 여부(true: 만료되지 않음, false: 만료됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 계정 사용가능 여부(true: 사용가능, false: 사용불가능)
    @Override
    public boolean isEnabled() {
        return false;
    }

    public static UserDetailDto of(User user) {
        return UserDetailDto.builder()
            .id(user.getId())
            .credential(user.getCredential())
            .name(user.getName())
            .principal(user.getPrincipal())
            .build();
    }


}
