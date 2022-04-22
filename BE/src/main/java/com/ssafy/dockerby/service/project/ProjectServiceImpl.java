package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.project.*;
import com.ssafy.dockerby.entity.project.*;
import com.ssafy.dockerby.entity.project.enums.StateType;
import com.ssafy.dockerby.entity.project.states.DockerBuild;
import com.ssafy.dockerby.entity.project.states.DockerRun;
import com.ssafy.dockerby.entity.project.states.GitPull;
import com.ssafy.dockerby.repository.project.ProjectRepository;
import com.ssafy.dockerby.repository.project.ProjectStateRepository;
import com.ssafy.dockerby.util.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {
  private final ProjectRepository projectRepository;
  private final ProjectStateRepository projectStateRepository;

  @Override
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
      project.updateState("Processing");

      // 저장완료 로그출력
      log.info("project save completed");
    }
    catch (Exception error){
      // 저장실패 로그출력
      log.error("project save failed",error.getCause(),error.getMessage());

      throw error;
    }

    return ProjectResponseDto.builder()
      .projectId(project.getId())
      .projectName(project.getProjectName())
      .state(String.valueOf(project.getStateType()))
      .configLocation(configLocation)
      .build();
  }

  @Override
  public ProjectState buildAndGetState(ProjectRequestDto projectRequestDto) throws ChangeSetPersister.NotFoundException {

    boolean successFlag=true;

    //GitPull start
    GitPull gitPull;
    try { // gitPull 트라이
      //TODO / ProjectService : GitPull 트라이

      // state Done 넣기
      gitPull = GitPull.builder()
        .stateType(StateType.valueOf("Done"))
        .build();
      //성공 로그 출력
      log.info(" Done : {}",gitPull.toString());
    }
    catch (Exception e){ // state failed 넣기
      //에러 로그 출력
      log.error("GitPull failed {} {}",e.getCause(),e.getMessage());

      //gitPullState failed 입력
      gitPull = GitPull.builder()
        .stateType(StateType.valueOf("Failed"))
        .build();

      //성공 플래그 false 로 변경
      successFlag=false;
    }


    //DockerBuild start
    DockerBuild dockerBuild;
    try { // dockerBuild 트라이
      //TODO / ProjectService : DockerBuild 트라이

      // state Done 넣기
      dockerBuild = DockerBuild.builder()
        .stateType(StateType.valueOf("Done"))
        .build();
    }
    catch (Exception e){ // state failed 넣기
      //에러 로그 출력
      log.error("DockerBuild failed {} {}",e.getCause(),e.getMessage());

      //dockerBuildState failed 입력
      dockerBuild = DockerBuild.builder()
        .stateType(StateType.valueOf("Failed"))
        .build();

      //성공 플래그 false 로 변경
      successFlag=false;
    }

    //DockerRun start
    DockerRun dockerRun;
    try { // dockerRun 트라이
      //TODO / ProjectService : DockerRun 트라이

      // state Done 넣기
      dockerRun = DockerRun.builder()
        .stateType(StateType.valueOf("Done"))
        .build();
    }
    catch (Exception e){ // state failed 넣기
      //에러 로그 출력
      log.error("DockerRun failed {} {}",e.getCause(),e.getMessage());

      //dockerRunState failed 입력
      dockerRun = DockerRun.builder()
        .stateType(StateType.valueOf("Failed"))
        .build();

      //성공 플래그 false 로 변경
      successFlag=false;
    }


    //프로젝트 상태 수정
    Project  project = projectRepository.findOneByProjectName(projectRequestDto.getProjectName())
      .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

    //모든 빌드 성공시 Done
    if(successFlag) {
      project.updateState("Done");
      log.info("update state to Done");
    }
    //하나라도 실패시 Failed
    else {
      project.updateState("Failed");
      log.info("update state to Failed");
    }

    //projectState , 각 빌드상태 save  : cascade
    ProjectState projectState = ProjectState.builder()
      .project(project)
      .gitPull(gitPull)
      .dockerBuild(dockerBuild)
      .dockerRun(dockerRun)
      .build();

    //build - state binding
    gitPull.updateProjectState(projectState);
    dockerBuild.updateProjectState(projectState);
    dockerRun.updateProjectState(projectState);

    projectStateRepository.save(projectState);

    return projectState;
  }

  @Override
  public StateResponseDto checkState(StateRequestDto stateRequestDto) throws ChangeSetPersister.NotFoundException {
    //TODO /ProjectSercice : checkState Test 작성

    //StateRequest 에서 받은 projectId로 DB 탐색
    ProjectState projectState = projectStateRepository.findByProjectId(stateRequestDto.getProjectId()).orElseThrow(() -> new ChangeSetPersister.NotFoundException());    
    //state initialize
    String state="";

    //buildType 에 따라서 각각의 state 입력
    if("GitPull".equals(stateRequestDto.getBuildType().toString())){
      state= projectState.getGitPull().getStateType().toString();
    }
    else if("DockerBuild".equals(stateRequestDto.getBuildType().toString())){
      state= projectState.getDockerBuild().getStateType().toString();
    }
    else if("DockerRun".equals(stateRequestDto.getBuildType().toString())){
      state= projectState.getDockerRun().getStateType().toString();
    }

    //성공 로그 출력
    log.info("ProjectService checkState success state : {}",state);


    //state 를 넣은 response 반환환
    return StateResponseDto.builder()
      .projectId(stateRequestDto.getProjectId())
      .buildType(stateRequestDto.getBuildType())
      .stateType(StateType.valueOf(state))
      .build();
  }
}
