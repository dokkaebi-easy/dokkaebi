package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.entity.project.enums.StateType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectListResponseDto {
    private Long projectId;
    private String projectName;
    private StateType state;


    public static ProjectListResponseDto from(Project project){
        if (project == null) {
            return null;
        }
        return  ProjectListResponseDto.builder()
            .projectId(project.getId())
            .projectName(project.getProjectName())
            .state(project.getStateType())
            .build();
    }
}
