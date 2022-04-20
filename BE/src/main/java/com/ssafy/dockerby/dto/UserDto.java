package com.ssafy.dockerby.dto;

import java.util.Objects;
import javax.persistence.Column;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDto userDto = (UserDto) o;
        return Objects.equals(getPrincipal(), userDto.getPrincipal())
            && Objects.equals(getCredential(), userDto.getCredential());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrincipal(), getCredential());
    }
}
