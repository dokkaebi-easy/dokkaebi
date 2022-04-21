package com.ssafy.dockerby.dto.User;

import com.ssafy.dockerby.entity.User.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    private Long id;
    private String principal;
    private String credential;
    private String name;
    private String authKey;

    public static UserDto of(User user) {
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .principal(user.getPrincipal())
            .build();
    }
}
