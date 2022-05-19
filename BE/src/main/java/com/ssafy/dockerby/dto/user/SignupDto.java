package com.ssafy.dockerby.dto.user;


import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupDto {
    @NotNull
    private String principal;
    @NotNull
    private String credential;
    @NotNull
    private String name;
    @NotNull
    private String authKey;

}
