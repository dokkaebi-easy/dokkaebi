package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.core.docker.DockerBuilder;
import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import com.ssafy.dockerby.dto.project.BuildTotalResponseDto;
import com.ssafy.dockerby.dto.project.ConfigHistoryListResponseDto;
import com.ssafy.dockerby.dto.project.FrameworkTypeResponseDto;
import com.ssafy.dockerby.dto.project.FrameworkVersionResponseDto;
import com.ssafy.dockerby.dto.project.ProjectListResponseDto;
import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import com.ssafy.dockerby.dto.project.StateDto;
import com.ssafy.dockerby.dto.project.StateRequestDto;
import com.ssafy.dockerby.dto.project.StateResponseDto;
import com.ssafy.dockerby.entity.ConfigHistory;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.entity.project.ProjectConfig;
import com.ssafy.dockerby.entity.project.ProjectState;
import com.ssafy.dockerby.entity.project.enums.StateType;
import com.ssafy.dockerby.entity.project.frameworks.FrameworkType;
import com.ssafy.dockerby.entity.project.states.Build;
import com.ssafy.dockerby.entity.project.states.Pull;
import com.ssafy.dockerby.entity.project.states.Run;
import com.ssafy.dockerby.entity.user.User;
import com.ssafy.dockerby.repository.project.BuildRepository;
import com.ssafy.dockerby.repository.project.FrameworkRepository;
import com.ssafy.dockerby.repository.project.FrameworkTypeRepository;
import com.ssafy.dockerby.repository.project.ProjectRepository;
import com.ssafy.dockerby.repository.project.ProjectStateRepository;
import com.ssafy.dockerby.repository.project.PullRepository;
import com.ssafy.dockerby.repository.project.RunRepository;
import com.ssafy.dockerby.repository.user.UserRepository;
import com.ssafy.dockerby.util.ConfigParser;
import com.ssafy.dockerby.util.FileManager;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
  private final com.ssafy.dockerby.repository.User.ConfigHistoryRepository configHistoryRepository;
  private final UserRepository userRepository;
  //TODO : buildNumber 입력 받아야합니다.
  private static int buildNumber=1;
  @Value("${dockerby.configRootPath}")
  private String configRootPath;

  @Value("${dockerby.logPath}")
  private String logPath;



  @Override
  public List<DockerContainerConfig> upsert(Principal principal,ProjectRequestDto projectRequestDto) {
    Project project = projectRepository.findOneByProjectName(projectRequestDto.getProjectName())
        .orElseGet(() ->
            projectRepository.save(Project.from(projectRequestDto)));

    List<DockerContainerConfig> buildConfigs = ConfigParser.getBuildConfig(projectRequestDto);

    List<ProjectConfig> configs = new ArrayList<>();
    buildConfigs.forEach(config -> configs.add(ProjectConfig.from(config.getName())));

    project.addConfig(configs);

    upsertConfigFile(projectRequestDto.getProjectName(), buildConfigs);
    //프로젝트 입력

    return buildConfigs;
  }

  private void upsertConfigFile(String projectName, List<DockerContainerConfig> buildConfigs) {
    StringBuilder filePath = new StringBuilder();
    filePath.append(configRootPath).append("/").append(projectName);
    buildConfigs.forEach(config -> {
      try {
        FileManager.saveJsonFile(filePath.toString(), config.getName(), config);
      } catch (IOException e) {
        log.error("", e);
      }
    });
  }

  private List<DockerContainerConfig> loadConfigFiles(List<ProjectConfig> configs) {

    return null;
  }

  private ProjectState createProjectState(Project project, Pull pull, Build build, Run run) {
    ProjectState projectState = ProjectState.of(Pull.from(), Build.from(), Run.from());
    projectState.setProject(project);

    pull.updateProjectState(projectState);
    build.updateProjectState(projectState);
    run.updateProjectState(projectState);

    //로그인 유저 탐색
    User user = userRepository.findByPrincipal(principal.getName())
        .orElseThrow(() -> new ChangeSetPersister.NotFoundException());;
    //히스토리 저장
    createHistory(user,project,"Create Project");

    return projectStateRepository.save(projectState);
  }


  @Override
  public ProjectState build(Long ProjectId)
      throws ChangeSetPersister.NotFoundException,IOException {
    //빌드 시작 로그 출력
    log.info("build start in service part");

    Project project = projectRepository.findById(ProjectId)
        .orElseThrow(() -> new NotFoundException());

    Pull pull = Pull.from();
    Build build = Build.from();
    Run run = Run.from();

    //프로젝트 상태 진행중으로 변경
    project.updateState(StateType.Processing);

    ProjectState projectState = createProjectState(project, pull, build, run);

    boolean successFlag = true;

    StringBuilder filePath = new StringBuilder();
    filePath.append(logPath).append("/").append(project.getProjectName());
    DockerBuilder dockerBuilder = new DockerBuilder(project.getProjectName());
    List<DockerContainerConfig> configs = loadConfigFiles(project.getConfigs());
    //Pull start
    try { // pull 트라이
      //TODO / ProjectService : GitPull 트라이

//      dockerBuilder.saveDockerfiles(configs);
      // pull 완료 build 진행중 update
      pull.updateStateType("Done");
      build.updateStateType("Processing");

      //프로젝트 스테이트 저장 dirtyCheck
      pullRepository.save(pull);
      buildRepository.save(build);

      //성공 로그 출력
      log.info(" Pull Done : {}", pull.toString());
    } catch (Exception e) { // state failed 넣기
      //pullState failed 입력
      pull.updateStateType("Failed");

      //프로젝트 스테이트 저장
      pullRepository.save(pull);

      //성공 플래그 false 로 변경
      successFlag = false;

      //에러 로그 출력
      log.error("Pull failed {} {}", e.getCause(), e.getMessage());

      throw e;
    }



    //Build start
    try { // Build 트라이
      //TODO / ProjectService : Build 트라이
//      List<String> buildCommands = dockerBuilder.getBuildCommands(configs);
//      CommandInterpreter.run(filePath.toString(),project.getProjectName(),buildNumber,buildCommands);

      // state Done 넣기
      build.updateStateType("Done");
      run.updateStateType("Processing");

      //프로젝트 스테이트 저장
      buildRepository.save(build);
      runRepository.save(run);

      //성공 로그 출력
      log.info("Build Done : {}", build.toString());
    } catch (Exception e) { // state failed 넣기
      //buildState failed 입력
      build.updateStateType("Failed");

      //프로젝트 스테이트 저장
      buildRepository.save(build);

      //성공 플래그 false 로 변경
      successFlag = false;

      //에러 로그 출력
      log.error("Build failed {} ", e);

      throw e;
    }

    //Run start
    try { // run 트라이
      //TODO / ProjectService : DockerRun 트라이
//      List<String> runCommands = dockerBuilder.getRunCommands(configs);
//      CommandInterpreter.run(filePath.toString(),project.getProjectName(),buildNumber,runCommands);
      // state Done 넣기
      run.updateStateType("Done");

      //프로젝트 스테이트 저장
      runRepository.save(run);

      //성공 로그 출력
      log.info("Run Done : {}", run.toString());
    } catch (Exception e) { // state failed 넣기
      //dockerRunState failed 입력
      run.updateStateType("Failed");

      //프로젝트 스테이트 저장
      runRepository.save(run);

      //성공 플래그 false 로 변경
      successFlag = false;

      //에러 로그 출력
      log.error("Run failed {} {}", e.getCause(), e.getMessage());

      throw e;
    }

    //모든 빌드 성공시 Done
    if (successFlag) {
      project.updateState(StateType.Done);
      log.info("update state to Done");
    }
    //하나라도 실패시 Failed
    else {
      project.updateState(StateType.Failed);
      log.info("update state to Failed");
    }

    //프로젝트 스테이트 저장
    projectRepository.save(project);

    return projectState;
  }

  @Override
  public StateResponseDto checkState(StateRequestDto stateRequestDto)
      throws ChangeSetPersister.NotFoundException {
    //TODO /ProjectSercice : checkState Test 작성

    //StateRequest 에서 받은 projectId로 DB 탐색
    ProjectState projectState = projectStateRepository.findByProjectId(
            stateRequestDto.getProjectId())
        .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    //state initialize
    String state = "";

    //buildType 에 따라서 각각의 state 입력
    if ("Pull".equals(stateRequestDto.getBuildType().toString())) {
      state = projectState.getPull().getStateType().toString();
    } else if ("Build".equals(stateRequestDto.getBuildType().toString())) {
      state = projectState.getBuild().getStateType().toString();
    } else if ("Run".equals(stateRequestDto.getBuildType().toString())) {
      state = projectState.getRun().getStateType().toString();
    }

    //성공 로그 출력
    log.info("ProjectService checkState success state : {}", state);

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

    try {
      // 모든 FrameworkType을 조회한 뒤 리스트에 담아서 반환
      frameworkTypeRepository.findAll().forEach(value ->
          frameworkTypes.add(FrameworkTypeResponseDto.from(value)));

      //성공 로그 출력
      log.info("getFrameworkType request success");
    } catch (Exception error) {
      //실패 로그 출력
      log.error("getFrameworkType request failed {} {}", error.getCause(), error.getMessage());

      throw error;
    }

    log.info("getFrameworkType request completed typeSize : {}", frameworkTypes.size());
    return frameworkTypes;
  }

  @Override
  public FrameworkVersionResponseDto getFrameworkVersion(Long typeId)
      throws ChangeSetPersister.NotFoundException {

    try { //framework 가져오기
      //frameworkTypeId 로 framework 가져오기
      FrameworkType type = frameworkTypeRepository.findById(typeId)
          .orElseThrow(() -> new NotFoundException());

      List<String> versions = new ArrayList<>();
      type.getLanguage().getVersions().forEach(version ->
          versions.add(version.getInputVersion()));

      List<String> buildTools = new ArrayList<>();
      if(!type.getBuildTools().isEmpty()) {
        type.getBuildTools().forEach(buildTool -> buildTools.add(buildTool.getName()));
      }
      //성공 로그 출력
      log.info("getFrameworkVersion request success");
      return FrameworkVersionResponseDto.from(versions,buildTools);
    } catch (Exception error) {
      //실패 로그 출력
      log.error("getFrameworkVersion request failed {} {}", error.getCause(), error.getMessage());
      throw error;
    }
  }

  @Override
  public List<ProjectListResponseDto> projectList(){
    log.info("Project List");
    List<Project> projectList = projectRepository.findAll();

    List<ProjectListResponseDto> resultList = new ArrayList<>();

    for(Project project : projectList){
      ProjectListResponseDto projectListDto = ProjectListResponseDto.from(project);
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
    configHistoryRepository.save(history);
    log.info("history save {} to {} detail-{}",history.getUser().getName(),history.getProject().getProjectName(),history.getMsg());
  }

  public List<ConfigHistoryListResponseDto>  historyList(){
    List<ConfigHistory> configHistories = configHistoryRepository.findAll(
        Sort.by(Sort.Direction.DESC, "registDate"));
    List<ConfigHistoryListResponseDto> resultList = new ArrayList<>();

    for(ConfigHistory configHistory : configHistories){
      ConfigHistoryListResponseDto configHistoryListDto = ConfigHistoryListResponseDto.from(configHistory);
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
