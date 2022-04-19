package com.ssafy.dockerby.service;

import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import com.ssafy.dockerby.dto.project.ProjectResponseDto;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.repository.ProjectRepository;
import com.ssafy.dockerby.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
  private final ProjectRepository projectRepository;
  private final JsonUtil jsonUtil;

  public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) throws IOException {
    //json 형태로 저장후 상대위치 반환
    String configLocation = jsonUtil.saveJson(projectRequestDto.getProjectName(),projectRequestDto.getSettingJson());

    //프로젝트 입력
    Project project = Project.of(projectRequestDto,configLocation);
    projectRepository.save(project);

    project.updateState("Progressing");
    log.info("project save 완료");

    return ProjectResponseDto.builder()
      .userId(project.getId())
      .projectName(project.getProjectName())
      .teamName(project.getTeamName())
      .state(project.getState())
      .configLocation(configLocation)
      .build();
  }
}
