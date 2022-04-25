package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.project.*;
import com.ssafy.dockerby.entity.ConfigHistory;
import com.ssafy.dockerby.entity.project.*;
import com.ssafy.dockerby.entity.project.enums.StateType;
import com.ssafy.dockerby.entity.project.frameworks.Framework;
import com.ssafy.dockerby.entity.project.frameworks.FrameworkType;
import com.ssafy.dockerby.entity.project.states.Build;
import com.ssafy.dockerby.entity.project.states.Run;
import com.ssafy.dockerby.entity.project.states.Pull;
import com.ssafy.dockerby.entity.user.User;
import com.ssafy.dockerby.repository.User.HistoryRepository;
import com.ssafy.dockerby.repository.user.UserRepository;
import com.ssafy.dockerby.repository.project.BuildRepository;
import com.ssafy.dockerby.repository.project.FrameworkRepository;
import com.ssafy.dockerby.repository.project.FrameworkTypeRepository;
import com.ssafy.dockerby.repository.project.ProjectRepository;
import com.ssafy.dockerby.repository.project.ProjectStateRepository;
import com.ssafy.dockerby.repository.project.PullRepository;
import com.ssafy.dockerby.repository.project.RunRepository;

import com.ssafy.dockerby.util.FileManager;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectStateRepository projectStateRepository;
  private final PullRepository pullRepository;
  private final BuildRepository buildRepository;
  private final RunRepository runRepository;

  private final FrameworkRepository frameworkRepository;
  private final FrameworkTypeRepository frameworkTypeRepository;

  private final HistoryRepository historyRepository;
  private final UserRepository userRepository;

  @Override
  public ProjectResponseDto createProject(Principal principal,ProjectRequestDto projectRequestDto)
      throws IOException, UserDefindedException, NotFoundException {
    //json 형태로 저장후 상대위치 반환
    String configLocation = "./jsonData/";
    FileManager.saveJsonFile(configLocation,projectRequestDto.getProjectName(),projectRequestDto.getSettingJson());

    //프로젝트 입력
    Project project = Project.of(projectRequestDto,configLocation);
    Pull pull= Pull.builder()
      .stateType(StateType.valueOf("Waiting"))
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

    try {
      // 프로젝트 빌드 state 진행중으로 변경
      project.updateState("Processing");
      project.addProjectState(projectState);

      pull.updateProjectState(projectState);
      build.updateProjectState(projectState);
      run.updateProjectState(projectState);

      //프로젝트 저장
      projectRepository.save(project);

      // 저장완료 로그출력
      log.info("project save completed");
    }
    catch (Exception error){
      // 저장실패 로그출력
      log.error("project save failed",error.getCause(),error.getMessage());

      throw error;
    }
    User user = userRepository.findByPrincipal(principal.getName())
        .orElseThrow(() -> new ChangeSetPersister.NotFoundException());;

    createHistory(user,project,"Create Project");
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

    //프로젝트 와 state 들 영속 부여
    Project  project = projectRepository.findOneByProjectName(projectRequestDto.getProjectName())
        .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

    ProjectState projectState = projectStateRepository.findByProjectId(project.getId())
      .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

    Pull pull= pullRepository.findByProjectStateId(projectState.getId())
      .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

    Build build = buildRepository.findByProjectStateId(projectState.getId())
      .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

    Run run = runRepository.findByProjectStateId(projectState.getId())
      .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

    //Pull start
    try { // pull 트라이
      //TODO / ProjectService : GitPull 트라이

      // pull 완료 build 진행중 update
      pull.updateStateType("Done");
      build.updateStateType("Processing");

      //프로젝트 스테이트 저장 dirtyCheck
      projectRepository.save(project);
      pullRepository.save(pull);
      buildRepository.save(build);

      //성공 로그 출력
      log.info(" Pull Done : {}",pull.toString());
    }
    catch (Exception e){ // state failed 넣기
      //pullState failed 입력
      pull.updateStateType("Failed");

      //프로젝트 스테이트 저장
      projectRepository.save(project);
      pullRepository.save(pull);

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
      build.updateStateType("Done");
      run.updateStateType("Processing");

      //프로젝트 스테이트 저장
      projectRepository.save(project);
      buildRepository.save(build);
      runRepository.save(run);

      //성공 로그 출력
      log.info("Build Done : {}",build.toString());
    }
    catch (Exception e){ // state failed 넣기
      //buildState failed 입력
      build.updateStateType("Failed");

      //프로젝트 스테이트 저장
      projectRepository.save(project);
      buildRepository.save(build);

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
      run.updateStateType("Done");

      //프로젝트 스테이트 저장
      projectRepository.save(project);
      runRepository.save(run);

      //성공 로그 출력
      log.info("Run Done : {}",run.toString());
    }
    catch (Exception e){ // state failed 넣기
      //dockerRunState failed 입력
      run.updateStateType("Failed");

      //프로젝트 스테이트 저장
      projectRepository.save(project);
      runRepository.save(run);

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

    //프로젝트 스테이트 저장
    projectRepository.save(project);

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


  @Override
  public List<FrameworkTypeResponseDto> getFrameworkType() {
    log.info("getFrameworkType request received");

    //frameworkTypes initialized
    List<FrameworkTypeResponseDto> frameworkTypes = new ArrayList<>();

    try { //framework 타입 가져오기 시작
      //모든 framework 가져오기
      List<FrameworkType> frameworks = frameworkTypeRepository.findAll();

      //각각 frameworkTypes 에 add 하기
      for(FrameworkType types : frameworks){
        FrameworkTypeResponseDto frameworkResponseDto = FrameworkTypeResponseDto.builder()
            .frameworkTypeId(types.getId())
            .frameworkName(types.getFrameworkName())
            .build();
        frameworkTypes.add(frameworkResponseDto);
      }

      //성공 로그 출력
      log.info("getFrameworkType request success");
    }
    catch (Exception error){
      //실패 로그 출력
      log.error("getFrameworkType request failed {} {}",error.getCause(),error.getMessage());

      throw error;
    }


    log.info("getFrameworkType request completed typeSize : {}",frameworkTypes.size());
    return frameworkTypes;
  }

  @Override
  public List<FrameworkVersionResponseDto> getFrameworkVersion(Long typeId) throws ChangeSetPersister.NotFoundException {
    //frameworkVersions initialized
    List<FrameworkVersionResponseDto> frameworkVersions = new ArrayList<>();

    try { //framework 가져오기
      //frameworkTypeId 로 framework 가져오기
      List<Framework> frameworks = frameworkRepository.findAllByFrameworkTypeId(typeId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

      for (Framework framework : frameworks) {
        FrameworkVersionResponseDto responseDto = FrameworkVersionResponseDto.builder()
            .frameworkBuildType(framework.getBuildType())
            .frameworkVersion(framework.getVersion())
            .build();
        frameworkVersions.add(responseDto);
      }
      //성공 로그 출력
      log.info("getFrameworkVersion request success");
    } catch (Exception error) {
      //실패 로그 출력
      log.error("getFrameworkVersion request failed {} {}", error.getCause(), error.getMessage());
      throw error;
    }
    log.info("getFrameworkVersion request completed VersionSize : {}", frameworkVersions.size());
    return frameworkVersions;
  }

  @Override
  public List<ProjectListDto> projectList(){
    log.info("Project List");
    List<Project> projectList = projectRepository.findAll();

    List<ProjectListDto> resultList = new ArrayList<>();

    for(Project project : projectList){
      ProjectListDto projectListDto = ProjectListDto.from(project);
      resultList.add(projectListDto);
    }


    log.info("project list size {}",resultList.size());
    return resultList;
  }

  //history 저장
  private void createHistory(User user, Project project,String detail){
    ConfigHistory history = ConfigHistory.builder()
        .user(user)
        .project(project)
        .msg(detail)
        .build();
    historyRepository.save(history);
    log.info("history save {} to {} detail-{}",history.getUser().getName(),history.getProject().getProjectName(),history.getMsg());
  }

  public List<ConfigHistoryListDto>  historyList(){
    List<ConfigHistory> configHistories = historyRepository.findAll(
        Sort.by(Sort.Direction.DESC, "registDate"));
    List<ConfigHistoryListDto> resultList = new ArrayList<>();

    for(ConfigHistory configHistory : configHistories){
      ConfigHistoryListDto configHistoryListDto = ConfigHistoryListDto.from(configHistory);
      resultList.add(configHistoryListDto);
    }


    log.info("ConfigHistory list size {}",resultList.size());
    return resultList;
  }


  @Override
  public List<BuildTotalResponseDto> buildTotal(Long projectId) throws NotFoundException {
    //responseDtos initialized
    List<BuildTotalResponseDto> responseDtos = new ArrayList<>();

    //해당 projectId의 projectState List로 받음
    List<ProjectState> projectStates = projectStateRepository.findAllByProjectId(projectId);

    //입력 시작 로그 출력
    log.info("projectState insert start  projectStateSize : {}",projectStates.size());

    //각각의 projectState에 대해 추출후 입력
    for(ProjectState projectState : projectStates){
      //각 state 들을 찾아옴
      Pull pull = pullRepository.findByProjectStateId(projectState.getId()).orElseThrow(() -> new NotFoundException());
      Build build = buildRepository.findByProjectStateId(projectState.getId()).orElseThrow(() -> new NotFoundException());
      Run run = runRepository.findByProjectStateId(projectState.getId()).orElseThrow(() -> new NotFoundException());

      StateDto stateDto = StateDto.builder()
        .pull(pull.getStateType())
        .build(build.getStateType())
        .run(run.getStateType())
        .build();

      BuildTotalResponseDto buildTotalResponseDto = BuildTotalResponseDto.builder()
        .projectStateId(projectState.getId())
        .state(stateDto)
        .build();

      //완성된 buildTotalResponseDto를 저장
      responseDtos.add(buildTotalResponseDto);
    }

    //완료 로그 출력
    log.info("projectState insert finished  responseDtoSize : {}",responseDtos.size());

    //responseDtos 리턴
    return responseDtos;
  }
}
