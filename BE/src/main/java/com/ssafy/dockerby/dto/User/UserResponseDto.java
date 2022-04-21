package com.ssafy.dockerby.dto.User;

import com.ssafy.dockerby.entity.User.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {

    private Long id;
    private String principal;
    private String name;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .name(user.getName())
            .principal(user.getPrincipal())
            .build();
    }

}
