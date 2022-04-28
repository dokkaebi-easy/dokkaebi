package com.ssafy.dockerby.dto.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponseDto {
    private String message;
    private String error;

    public static ErrorResponseDto from(Exception e) {
        return ErrorResponseDto.builder()
            .message(e.getMessage())
            .error(e.getClass().getSimpleName())
            .build();
    }
}
