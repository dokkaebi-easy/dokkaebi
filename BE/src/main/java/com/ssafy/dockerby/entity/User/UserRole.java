package com.ssafy.dockerby.entity.User;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("관리자"), MANAGER("매니저"), USER("일반사용자");

    private String description;

    UserRole(String description) {
        this.description = description;
    }
}
