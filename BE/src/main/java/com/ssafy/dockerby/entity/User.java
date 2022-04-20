package com.ssafy.dockerby.entity;

import com.ssafy.dockerby.dto.UserDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60,unique = true)
    private String principal;

    private String credential;

    @Column(length = 60, unique = true)
    private String name;
    //생성 매서드
    public static User from(UserDto userDto) {
        return User.builder()
            .id(userDto.getId())
            .credential(userDto.getCredential())
            .name(userDto.getName())
            .principal(userDto.getPrincipal())
            .build();
    }
}