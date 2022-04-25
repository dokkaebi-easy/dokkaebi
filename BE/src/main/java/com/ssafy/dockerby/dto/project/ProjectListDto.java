package com.ssafy.dockerby.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.ssafy.dockerby.entity.ConfigHistory;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.entity.project.enums.StateType;
import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectListDto {
    private Long projectId;
    private String projectName;
    private StateType state;


    public static ProjectListDto from(Project project){
        if (project == null) {
            return null;
        }
        return  ProjectListDto.builder()
            .projectId(project.getId())
            .projectName(project.getProjectName())
            .state(project.getStateType())
            .build();
    }
}
