package com.ssafy.dockerby.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.entity.project.enums.StateType;
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
    private StateType state;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastSuccessDate;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastFailDate;

    @Nullable
    private String lastDuration;

    public static ProjectListResponseDto from(Project project){
        if (project == null) {
            return null;
        }
        return  ProjectListResponseDto.builder()
            .projectId(project.getId())
            .projectName(project.getProjectName())
            .state(project.getStateType())
            .lastSuccessDate(project.getLastSuccessDate())
            .lastFailDate(project.getLastFailDate())
            .lastDuration(project.getLastDuration())
            .build();
    }
}
