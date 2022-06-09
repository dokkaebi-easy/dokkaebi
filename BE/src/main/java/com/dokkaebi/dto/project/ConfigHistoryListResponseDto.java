package com.dokkaebi.dto.project;

import com.dokkaebi.entity.ConfigHistory;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigHistoryListResponseDto {

    private String projectName;
    private String userName;
    private String msg;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime registDate;

    public static ConfigHistoryListResponseDto from(ConfigHistory configHistory) {
        if (configHistory == null) {
            return null;
        }
        return ConfigHistoryListResponseDto.builder()
            .projectName(configHistory.getProject().getProjectName())
            .userName(configHistory.getUser().getName())
            .msg(configHistory.getMsg())
            .registDate(configHistory.getRegistDate())
            .build();
    }
}

