package com.ssafy.dockerby.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.entity.project.enums.StateType;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import lombok.*;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.lang.Nullable;

import javax.persistence.Convert;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectListResponseDto {
    @NotNull
    private Long projectId;

    @NotNull
    private String projectName;

    @NotNull
    private String state;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastSuccessDate;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastFailDate;

    @Nullable
    private String lastDuration;

    @NotNull
    private String recentBuildDate;

    @Nullable
    private List<Map<String,String>> ports;

    public static ProjectListResponseDto of(Project project,List<Map<String,String>> ports){
        String buildDate = "";

        if (project == null) {
            return null;
        }
        if(project.getRecentBuildDate() != null){
            buildDate = project.getRecentBuildDate().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return  ProjectListResponseDto.builder()
            .projectId(project.getId())
            .projectName(project.getProjectName())
            .state(project.getStateType().getName())
            .lastSuccessDate(project.getLastSuccessDate())
            .lastFailDate(project.getLastFailDate())
            .lastDuration(project.getLastDuration())
            .recentBuildDate(buildDate)
            .ports(ports)
            .build();
    }
}
