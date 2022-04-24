package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.project.*;
import com.ssafy.dockerby.entity.project.*;
import com.ssafy.dockerby.entity.project.enums.StateType;
import com.ssafy.dockerby.entity.project.states.Build;
import com.ssafy.dockerby.entity.project.states.Run;
import com.ssafy.dockerby.entity.project.states.Pull;
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
  public ProjectState build(ProjectRequestDto projectRequestDto) throws ChangeSetPersister.NotFoundException {

    boolean successFlag=true;

    //프로젝트 상태 수정
    Project  project = projectRepository.findOneByProjectName(projectRequestDto.getProjectName())
      .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

    Pull pull= Pull.builder()
      .stateType(StateType.valueOf("Processing"))
      .build();
    Build build = Build.builder()
      .stateType(StateType.valueOf("Waiting"))
      .build();
    Run run = Run.builder()
      .stateType(StateType.valueOf("Waiting"))
      .build();

    ProjectState projectState = ProjectState.builder()
      .project(project)
      .pull(pull)
      .build(build)
      .run(run)
      .build();

    //프로젝트 스테이트 저장
    saveProjectState(projectState,project,pull,build,run);

    //Pull start
    try { // pull 트라이
      //TODO / ProjectService : GitPull 트라이

      // state Done 넣기
      pull = Pull.builder()
        .stateType(StateType.valueOf("Done"))
        .build();

      //프로젝트 스테이트 저장
      saveProjectState(projectState,project,pull,build,run);

      //성공 로그 출력
      log.info(" Pull Done : {}",pull.toString());
    }
    catch (Exception e){ // state failed 넣기
      //pullState failed 입력
      pull = Pull.builder()
        .stateType(StateType.valueOf("Failed"))
        .build();

      //프로젝트 스테이트 저장
      saveProjectState(projectState,project,pull,build,run);

      //성공 플래그 false 로 변경
      successFlag=false;

      //에러 로그 출력
      log.error("Pull failed {} {}",e.getCause(),e.getMessage());

      throw e;
    }


    //Build start
    try { // Build 트라이
      //TODO / ProjectService : Build 트라이

      // state Done 넣기
      build = Build.builder()
        .stateType(StateType.valueOf("Done"))
        .build();

      //프로젝트 스테이트 저장
      saveProjectState(projectState,project,pull,build,run);

      //성공 로그 출력
      log.info("Build Done : {}",build.toString());
    }
    catch (Exception e){ // state failed 넣기
      //buildState failed 입력
      build = Build.builder()
        .stateType(StateType.valueOf("Failed"))
        .build();

      //프로젝트 스테이트 저장
      saveProjectState(projectState,project,pull,build,run);

      //성공 플래그 false 로 변경
      successFlag=false;

      //에러 로그 출력
      log.error("Build failed {} {}",e.getCause(),e.getMessage());

      throw e;
    }

    //Run start
    try { // run 트라이
      //TODO / ProjectService : DockerRun 트라이

      // state Done 넣기
      run = Run.builder()
        .stateType(StateType.valueOf("Done"))
        .build();

      //프로젝트 스테이트 저장
      saveProjectState(projectState,project,pull,build,run);

      //성공 로그 출력
      log.info("Run Done : {}",run.toString());
    }
    catch (Exception e){ // state failed 넣기
      //dockerRunState failed 입력
      run = Run.builder()
        .stateType(StateType.valueOf("Failed"))
        .build();

      //프로젝트 스테이트 저장
      saveProjectState(projectState,project,pull,build,run);

      //성공 플래그 false 로 변경
      successFlag=false;

      //에러 로그 출력
      log.error("Run failed {} {}",e.getCause(),e.getMessage());

      throw e;
    }




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

    //build - state binding
    pull.updateProjectState(projectState);
    build.updateProjectState(projectState);
    run.updateProjectState(projectState);

    //프로젝트 스테이트 저장
    saveProjectState(projectState,project,pull,build,run);

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
    if("Pull".equals(stateRequestDto.getBuildType().toString())){
      state= projectState.getPull().getStateType().toString();
    }
    else if("Build".equals(stateRequestDto.getBuildType().toString())){
      state= projectState.getBuild().getStateType().toString();
    }
    else if("Run".equals(stateRequestDto.getBuildType().toString())){
      state= projectState.getRun().getStateType().toString();
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

  private void saveProjectState(ProjectState projectState,Project project,Pull pull,Build build,Run run){

    projectState = ProjectState.builder()
      .project(project)
      .pull(pull)
      .build(build)
      .run(run)
      .build();

    projectStateRepository.save(projectState);
  }
}
