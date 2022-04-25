package com.ssafy.dockerby.dto.User;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class SignupDto {
    @JsonProperty(value="id")
    private String principal;
    @JsonProperty(value="password")
    private String credential;
    private String name;
    private String authKey;

}
