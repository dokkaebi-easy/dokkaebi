package com.ssafy.dockerby.service;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import com.ssafy.dockerby.dto.project.ProjectResponseDto;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.repository.ProjectRepository;
import com.ssafy.dockerby.util.FileManager;
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

  public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) throws IOException, UserDefindedException {
    //json 형태로 저장후 상대위치 반환
    String configLocation = "./jsonData/";
    FileManager.saveJsonFile(configLocation,projectRequestDto.getProjectName(),projectRequestDto.getSettingJson());

    //프로젝트 입력
    Project project = Project.of(projectRequestDto,configLocation);

    try {
      //프로젝트 저장
      projectRepository.save(project);

      // 프로젝트 빌드 state 진행중으로 변경
      project.updateState("Progressing");

      // 저장완료 로그출력
      log.info("project save completed");
    }
    catch (Exception e){
      // 저장실패 로그출력
      log.error("project save failed",e.getCause(),e.getMessage());

      //TODO : 예외처리 - 저장실패
      throw new IOException();
    }

    return ProjectResponseDto.builder()
      .projectId(project.getId())
      .projectName(project.getProjectName())
      .state(project.getState())
      .configLocation(configLocation)
      .build();
  }
}
