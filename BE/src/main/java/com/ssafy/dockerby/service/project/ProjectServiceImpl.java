package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.core.docker.DockerAdapter;
import com.ssafy.dockerby.core.docker.EtcConfigMaker;
import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import com.ssafy.dockerby.core.docker.dto.DockerNginxConfig;
import com.ssafy.dockerby.core.gitlab.GitlabAdapter;
import com.ssafy.dockerby.core.gitlab.dto.GitlabCloneDto;
import com.ssafy.dockerby.core.gitlab.dto.GitlabWebHookDto;
import com.ssafy.dockerby.core.util.CommandInterpreter;
import com.ssafy.dockerby.dto.project.BuildConfigDto;
import com.ssafy.dockerby.dto.project.BuildConfigDto.ConfigProperty;
import com.ssafy.dockerby.dto.project.BuildDetailRequestDto;
import com.ssafy.dockerby.dto.project.BuildDetailResponseDto;
import com.ssafy.dockerby.dto.project.BuildTotalResponseDto;
import com.ssafy.dockerby.dto.project.ConfigHistoryListResponseDto;
import com.ssafy.dockerby.dto.project.FrameworkTypeResponseDto;
import com.ssafy.dockerby.dto.project.FrameworkVersionResponseDto;
import com.ssafy.dockerby.dto.project.GitConfigDto;
import com.ssafy.dockerby.dto.project.NginxConfigDto;
import com.ssafy.dockerby.dto.project.ProjectConfigDto;
import com.ssafy.dockerby.dto.project.ProjectListResponseDto;
import com.ssafy.dockerby.dto.project.StateDto;
import com.ssafy.dockerby.dto.user.UserDetailDto;
import com.ssafy.dockerby.entity.ConfigHistory;
import com.ssafy.dockerby.entity.core.FrameworkType;
import com.ssafy.dockerby.entity.core.Version;
import com.ssafy.dockerby.entity.git.GitlabAccessToken;
import com.ssafy.dockerby.entity.git.WebhookHistory;
import com.ssafy.dockerby.entity.project.BuildState;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.entity.project.ProjectConfig;
import com.ssafy.dockerby.entity.project.enums.BuildType;
import com.ssafy.dockerby.entity.project.enums.StateType;
import com.ssafy.dockerby.entity.user.User;
import com.ssafy.dockerby.repository.project.BuildStateRepository;
import com.ssafy.dockerby.repository.project.ConfigHistoryRepository;
import com.ssafy.dockerby.repository.project.FrameworkTypeRepository;
import com.ssafy.dockerby.repository.project.ProjectRepository;
import com.ssafy.dockerby.repository.user.UserRepository;
import com.ssafy.dockerby.service.git.GitlabService;
import com.ssafy.dockerby.util.ConfigParser;
import com.ssafy.dockerby.util.FileManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javassist.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

  private final EntityManager em;
  private final ProjectRepository projectRepository;
  private final BuildStateRepository buildStateRepository;
  private final FrameworkTypeRepository frameworkTypeRepository;
  private final ConfigHistoryRepository configHistoryRepository;
  private final UserRepository userRepository;
  private final GitlabService gitlabService;

  @Value("${dockerby.rootPath}")
  private String rootPath;
  @Value("${dockerby.configPath}")
  private String configPath;
  @Value("${dockerby.logPath}")
  private String logPath;

  @Override
  public Optional<Project> findProjectByName(String name) {
    return projectRepository.findOneByProjectName(name);
  }

  @Override
  public ProjectConfigDto findConfigById(Long projectId)
      throws NotFoundException, IOException {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(
            () -> new NotFoundException("ProjectServiceImpl.configByProjectName : " + projectId));

    StringBuilder filePath = new StringBuilder();
    filePath.append(rootPath).append("/").append(project.getProjectName()).append("/")
        .append(configPath);

    List<DockerContainerConfig> configs = loadConfigFiles(filePath.toString(),
        project.getProjectConfigs(), DockerContainerConfig.class);

    NginxConfigDto nginxConfigDto = null;
    for (DockerContainerConfig config : configs) {
      if (config.isUseNginx()) {
        nginxConfigDto = NginxConfigDto.from(loadConfigFilesByfileName(filePath.toString(), "nginx",
            DockerNginxConfig.class));
        break;
      }

    }

    List<BuildConfigDto> buildConfigs = new ArrayList<>();
    for (DockerContainerConfig config : configs) {
      Version version = frameworkTypeRepository.findById(
              Long.valueOf(config.getFramework().ordinal()))
          .orElseThrow(() -> new NotFoundException(
              "ProjectServiceImpl.configByProjectName : FrameworkType does not exist : "
                  + config.getFramework()))
          .getLanguage().findVersionByDocker(config.getVersion())
          .orElseThrow(() -> new NotFoundException(
              "ProjectServiceImpl.configByProjectName : FrameworkType does not exist : "
                  + config.getVersion()));

      List<ConfigProperty> properties = ConfigParser.dockerContainerPropertyToConfigProperty(
          config.getProperties());
      buildConfigs.add(BuildConfigDto.from(config, version.getInputVersion(), properties));
    }
    return ProjectConfigDto.of(projectId, project.getProjectName(), buildConfigs,
        GitConfigDto.from(project.getGitConfig()), nginxConfigDto);


  }

  @Override
  public Map<Project, String> upsert(ProjectConfigDto projectConfigDto)
      throws NotFoundException, IOException {

    Map<Project, String> result = new HashMap<>();
    Project project;
    if (projectConfigDto.getProjectId() != 0) {
      project = projectRepository.findById(projectConfigDto.getProjectId())
          .orElseThrow(() -> new NotFoundException(
              "upsert user not found id " + projectConfigDto.getProjectId()));
      result.put(project, "update");
    } else {
      project = projectRepository.save(Project.from(projectConfigDto));
      result.put(project, "create");
    }

    // 빌드 환경설정 Convert
    List<DockerContainerConfig> buildConfigs = ConfigParser.getBuildConfig(projectConfigDto);

    for (DockerContainerConfig dockerConfig : buildConfigs) {
      FrameworkType frameworkType = frameworkTypeRepository.findById(
              (long) dockerConfig.getFramework().ordinal())
          .orElseThrow(() -> new NotFoundException(
              "ProjectServiceImpl.upsert frameworkType : " + (long) dockerConfig.getFramework()
                  .ordinal()));
      dockerConfig.convertVersion(
          frameworkType.getLanguage().findVersionByInput(dockerConfig.getVersion())
              .orElseThrow(() -> new NotFoundException(
                  "ProjectServiceImpl.upsert version does not exists."
                      + (long) dockerConfig.getFramework().ordinal()))
              .getDockerVersion());
    }

    // Build config 내역을 ProjectConfig Table에 등록
    List<ProjectConfig> configs = new ArrayList<>();
    buildConfigs.forEach(config -> configs.add(ProjectConfig.from(config.getName())));

    project.addProjectConfigs(configs);

    StringBuilder filePath = new StringBuilder();
    filePath.append(rootPath + "/" + project.getProjectName());

    StringBuilder configFilePath = new StringBuilder();
    configFilePath.append(filePath).append("/").append(configPath);

    upsertConfigFile(configFilePath.toString(), buildConfigs);
    log.info("GitConfigDto project ID : {}", project.getId());
    GitConfigDto getConfigDto = projectConfigDto.getGitConfig();
    if (!getConfigDto.checkEmpty()) {
      gitlabService.config(project.getId())
          .map(config -> gitlabService.updateConfig(project, getConfigDto))
          .orElseGet(() -> gitlabService.createConfig(project, getConfigDto));

      StringBuilder repositoryPath = new StringBuilder();
      repositoryPath.append(filePath).append("/").append(getConfigDto.getGitProjectId());

      // Git clone

      FileUtils.deleteDirectory(new File(repositoryPath.toString()));

      GitlabAccessToken token = gitlabService.token(getConfigDto.getAccessTokenId());

      String cloneCommand = GitlabAdapter.getCloneCommand(
          GitlabCloneDto.of(token.getAccessToken(), getConfigDto.getRepositoryUrl(),
              getConfigDto.getBranchName(), getConfigDto.getGitProjectId()));

      CommandInterpreter.runDestPath(filePath.toString(),
          new StringBuilder().append(filePath).append("/").append(logPath).toString(), "Clone", 0,
          cloneCommand);


      DockerAdapter dockerAdapter = new DockerAdapter(
          new StringBuilder().append(filePath).append("/").append(getConfigDto.getGitProjectId())
              .toString(), project.getProjectName());

      try {
        dockerAdapter.saveDockerfiles(buildConfigs);
      } catch (Exception e) {
        log.error("docker file not making {} DockerAdapter({})",project.getProjectName());
      }
      if (!projectConfigDto.getNginxConfig().isNotUse()) {
        String nginxFrontProjectDirectory = "";
        for (DockerContainerConfig config : buildConfigs) {
          if (config.isUseNginx()) {
            nginxFrontProjectDirectory = config.getProjectDirectory();
            break;
          }
        }
        if (nginxFrontProjectDirectory.isEmpty()) {
          throw new IllegalArgumentException(
              "ProjectServiceImpl.upsert nginxconf 존재하지만 사용하는 prj가 없음");
        }
        StringBuilder nginxPath = new StringBuilder();
        nginxPath.append(filePath).append("/").append(getConfigDto.getGitProjectId())
            .append(nginxFrontProjectDirectory);
        EtcConfigMaker.nginxConfig(nginxPath.toString(),
            DockerNginxConfig.from(projectConfigDto.getNginxConfig()));
        EtcConfigMaker.saveDockerNginxConfig(configFilePath.toString(),
            projectConfigDto.getNginxConfig());
      }
    }

    return result;
  }

  /**
   * Project build config를 json 형태로 저장 내부적으로 projects/{projectName}/jsonData으로 경로를 지정한다. 파일 이름은
   * build하는 configName이다.
   *
   * @param filePath     환경 설정 파일 저장 위치
   * @param buildConfigs FE로부터 입력받은 빌드 환경설정 dto
   */
  private void upsertConfigFile(String filePath, List<DockerContainerConfig> buildConfigs) {
    buildConfigs.forEach(config -> {
      try {
        FileManager.saveJsonFile(filePath, config.getName(), config);
      } catch (IOException e) {
        log.error("", e);
      }
    });
  }

  private <T> List<T> loadConfigFiles(String filePath,
      List<ProjectConfig> configs, Class<T> type)
      throws IOException {
    List<T> results = new ArrayList<>();

    for (ProjectConfig config : configs) {
      results.add(
          FileManager.loadJsonFile(
              filePath,
              config.getFileName(),
              type));
    }
    return results;
  }

  private <T> T loadConfigFilesByfileName(String filePath,
      String fileName, Class<T> type)
      throws IOException {
    T result = FileManager.loadJsonFile(filePath, fileName, type);

    return result;
  }

  private List<BuildState> createBuildState(Project project,GitlabWebHookDto webHookDto) {
    List<BuildState> buildStates = new ArrayList<>();
    Long buildNumber = Long.valueOf(buildStateRepository.findAllByProjectIdOrderByBuildNumberAsc(project.getId()).size()/3);

    BuildState buildState = BuildState.builder()
      .project(project)
      .buildNumber(buildNumber)
      .buildType(BuildType.valueOf("Pull"))
      .stateType(StateType.valueOf("Waiting"))
      .build();
    if (webHookDto != null) {
      WebhookHistory webhookHistory = WebhookHistory.of(webHookDto);
      webhookHistory.setBuildState(buildState);
      buildState.setWebhookHistory(webhookHistory);
    }

    buildStateRepository.save(buildState);
    buildStates.add(buildState);

    BuildState buildState1 = BuildState.builder()
      .project(project)
      .buildNumber(buildNumber)
      .buildType(BuildType.valueOf("Build"))
      .stateType(StateType.valueOf("Waiting"))
      .build();

    buildStateRepository.save(buildState1);
    buildStates.add(buildState1);

    BuildState buildState2 = BuildState.builder()
      .project(project)
      .buildNumber(buildNumber)
      .buildType(BuildType.valueOf("Run"))
      .stateType(StateType.valueOf("Waiting"))
      .build();

    buildStateRepository.save(buildState2);
    buildStates.add(buildState2);

    return buildStates;
  }

  @Override
  public void build(Long projectId, GitlabWebHookDto webHookDto)
      throws NotFoundException, IOException {
    //수동 commit을 위한 transaction 추가
    EntityTransaction transaction = em.getTransaction();
    //빌드 시작 로그 출력
    log.info("build start in service part");

    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new NotFoundException("ProjectSerivceImpl.build : " + projectId));

    //프로젝트 상태 진행중으로 변경
    project.updateState(StateType.Processing);

    List<BuildState> buildStates = createBuildState(project,webHookDto);

    em.persist(project);


    // 경로
    StringBuilder filePath = new StringBuilder();
    filePath.append(rootPath).append("/").append(project.getProjectName());

    StringBuilder logFilePath = new StringBuilder();
    logFilePath.append(filePath).append("/").append(logPath);

    StringBuilder configFilePath = new StringBuilder();
    configFilePath.append(filePath).append("/").append(configPath);

    StringBuilder repositoryPath = new StringBuilder();
    repositoryPath.append(filePath).append("/").append(project.getGitConfig().getGitProjectId());

    DockerAdapter dockerAdapter = new DockerAdapter(repositoryPath.toString(),
        project.getProjectName());
    List<DockerContainerConfig> configs = loadConfigFiles(configFilePath.toString(),
        project.getProjectConfigs(), DockerContainerConfig.class);

    int buildNumber = Integer.parseInt(buildStates.get(0).getBuildNumber().toString());
    //Pull start
    try { // pull 트라이
      if (buildStates.get(0).getWebhookHistory() != null) {
        List<String> commands = new ArrayList<>();
        commands.add(GitlabAdapter.getPullCommand(webHookDto.getDefaultBranch()));
        CommandInterpreter.runDestPath(repositoryPath.toString(), logFilePath.toString(), "Pull",
            buildNumber, commands);

        dockerAdapter.saveDockerfiles(configs);
      }
      // pull 완료 build 진행중 update
      buildStates.get(0).updateStateType("Done");
      buildStates.get(1).updateStateType("Processing");


      //dirtyCheck 후 flush
      em.persist(buildStates);
      transaction.commit();
      //성공 로그 출력
      log.info(" Pull Done : {}", buildStates.get(0).toString());
    } catch (Exception e) { // state failed 넣기
      //pullState failed 입력
      buildStates.get(0).updateStateType("Failed");
      project.updateState(StateType.Failed);
      log.info("update state to Failed");

      em.persist(buildStates);
      transaction.commit();

      //에러 로그 출력
      log.error("Pull failed {} {}", e.getCause(), e.getMessage());

      throw e;
    }

    //Build start
    try { // Build 트라이
      List<String> buildCommands = dockerAdapter.getBuildCommands(configs);
      CommandInterpreter.run(logFilePath.toString(), "Build", buildNumber, buildCommands);

      // state Done 넣기
      buildStates.get(1).updateStateType("Done");
      buildStates.get(2).updateStateType("Processing");

      //dirtyCheck 후 flush
      em.persist(buildStates);
      transaction.commit();

      //성공 로그 출력
      log.info("Build Done : {}", buildStates.get(1).toString());
    } catch (Exception e) { // state failed 넣기
      //buildState failed 입력
      buildStates.get(1).updateStateType("Failed");
      project.updateState(StateType.Failed);

      //dirtyCheck 후 flush
      em.persist(buildStates);
      transaction.commit();

      //에러 로그 출력
      log.error("Build failed {} ", e);

      throw e;
    }

    //Run start
    try { // run 트라이
      if (buildNumber != 1) {
        CommandInterpreter.run(logFilePath.toString(), "Remove", buildNumber,
            dockerAdapter.getRemoveCommands(configs));
      }
      List<String> buildCommands = dockerAdapter.getRunCommands(configs);
      CommandInterpreter.run(logFilePath.toString(), "Run", buildNumber, buildCommands);
      // state Done 넣기
      buildStates.get(2).updateStateType("Done");

      //dirtyCheck 후 flush
      em.persist(buildStates);
      transaction.commit();

      //성공 로그 출력
      log.info("Run Done : {}", buildStates.get(2).toString());
    } catch (Exception e) { // state failed 넣기
      //dockerRunState failed 입력
      buildStates.get(2).updateStateType("Failed");
      project.updateState(StateType.Failed);

      //dirtyCheck 후 flush
      em.persist(buildStates);
      transaction.commit();

      //에러 로그 출력
      log.error("Run failed {} {}", e.getCause(), e.getMessage());

      throw e;
    }

    //모든 빌드 성공시 Done
    log.info("update state to Done");
    project.updateState(StateType.Done);

    //dirtyCheck 후 flush
    em.persist(buildStates);
    transaction.commit();
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
  public FrameworkVersionResponseDto getFrameworkVersion(Long typeId) throws NotFoundException {

    try { //framework 가져오기
      //frameworkTypeId 로 framework 가져오기
      FrameworkType type = frameworkTypeRepository.findById(typeId)
          .orElseThrow(
              () -> new NotFoundException("ProjectServiceImpl.getFrameworkVersion : " + typeId));

      List<String> versions = new ArrayList<>();
      type.getLanguage().getVersions().forEach(version ->
          versions.add(version.getInputVersion()));

      List<String> buildTools = new ArrayList<>();
      if (!type.getBuildTools().isEmpty()) {
        type.getBuildTools().forEach(buildTool -> buildTools.add(buildTool.getName()));
      }
      //성공 로그 출력
      log.info("getFrameworkVersion request success");
      return FrameworkVersionResponseDto.from(type.getLanguage().getName(), versions, buildTools);
    } catch (Exception error) {
      //실패 로그 출력
      log.error("getFrameworkVersion request failed {} {}", error.getCause(), error.getMessage());
      throw error;
    }
  }


  //history 저장
  public void createConfigHistory(HttpServletRequest request, Project project, String msg)
      throws NotFoundException {
    //세션 정보 가져오기
    HttpSession session = request.getSession();
    UserDetailDto userDetailDto = (UserDetailDto) session.getAttribute("user");
    log.info("session User Pricipal : {} ", userDetailDto.getUsername());

    //로그인 유저 탐색
    User user = userRepository.findByPrincipal(userDetailDto.getUsername())
        .orElseThrow(() -> new NotFoundException(
            "ProjectServiceImpl.createConfigHistory : " + userDetailDto.getUsername()));
    log.info("user find username : {} ", user.getName());

    ConfigHistory history = ConfigHistory.builder()
        .user(user)
        .project(project)
        .msg(msg)
        .build();
    //configHistory 저장
    configHistoryRepository.save(history);
    log.info("history save {} to {} detail-{}", history.getUser().getName(),
        history.getProject().getProjectName(), history.getMsg());
  }

  public List<ConfigHistoryListResponseDto> historyList() {
    List<ConfigHistory> configHistories = configHistoryRepository.findAll(
        Sort.by(Sort.Direction.DESC, "registDate"));
    List<ConfigHistoryListResponseDto> resultList = new ArrayList<>();

    for (ConfigHistory configHistory : configHistories) {
      ConfigHistoryListResponseDto configHistoryListDto = ConfigHistoryListResponseDto.from(
          configHistory);
      resultList.add(configHistoryListDto);
    }

    log.info("ConfigHistory list size {}", resultList.size());
    return resultList;
  }


  @Override
  public List<BuildTotalResponseDto> buildTotal(Long projectId) throws NotFoundException {
    //responseDtos initialized
    List<BuildTotalResponseDto> responseDtos = new ArrayList<>();

    //해당 projectId의 buildState List로 받음
    List<BuildState> buildStates = buildStateRepository.findAllByProjectIdOrderByBuildNumberAsc(projectId); // sort따로 지정하기 라스트 보장x

    //입력 시작 로그 출력
    log.info("buildState insert start  buildStateSize : {}", buildStates.size());

    int counter=0;

    String pullStateType="";
    String buildStateType="";
    String runStateType="";

    //각각의 buildState에 대해 추출후 입력
    for (BuildState buildState : buildStates) { // 3개 세트

      if("Pull".equals(buildState.getBuildType().toString())){
        pullStateType=buildState.getStateType().toString();
      }
      else if("Build".equals(buildState.getBuildType().toString())){
        buildStateType=buildState.getStateType().toString();
      }
      else if("Run".equals(buildState.getBuildType().toString())){
        runStateType=buildState.getStateType().toString();
      }

      if(counter==2){
        StateDto stateDto = StateDto.builder()
          .pull(StateType.valueOf(pullStateType))
          .build(StateType.valueOf(buildStateType))
          .run(StateType.valueOf(runStateType))
          .build();

        BuildTotalResponseDto buildTotalResponseDto = BuildTotalResponseDto.builder()
          .buildStateId(buildState.getId())
          .buildNumber(buildState.getBuildNumber())
          .state(stateDto)
          .build();

        //완성된 buildTotalResponseDto를 저장
        responseDtos.add(buildTotalResponseDto);
        counter=0;
      }
      counter++;
    }

    //완료 로그 출력
    log.info("buildState insert finished  responseDtoSize : {}", responseDtos.size());

    //responseDtos 리턴
    return responseDtos;
  }

  @Override
  public BuildDetailResponseDto buildDetail(BuildDetailRequestDto buildDetailRequestDto)
      throws NotFoundException {
    //요청 로그 출력
    log.info("buildDetail Service start");

    BuildState buildState = buildStateRepository.findById(buildDetailRequestDto.getId())
        .orElseThrow(() -> new NotFoundException(
            "ProjectServiceImpl.buildDetail : Not found build state "
                + buildDetailRequestDto.getId()));

    log.info("buildState receive success {}", buildState.getProject().getProjectName());

    String path = rootPath + "/" + buildState.getProject().getProjectName()
        + "/log";//경로  projects/{프로젝트 이름}/log
    String fileName =
        (buildDetailRequestDto.getName().toString()) + "_"
            + buildState.getBuildNumber();//  상태_빌드 넘버

    StringBuilder consoleLog = new StringBuilder();

    //
    try {
      consoleLog.append(FileManager.loadFile(path, fileName));
    } catch (Exception error) {
      consoleLog.append("There is no console file !!");
    }

    BuildDetailResponseDto.GitInfo gitInfo = null;
    if (buildState.getWebhookHistory() != null) {
      gitInfo = BuildDetailResponseDto.GitInfo.builder()
          .username(buildState.getWebhookHistory().getUsername())
          .gitRepositoryUrl(buildState.getWebhookHistory().getGitHttpUrl())
          .gitBranch(buildState.getWebhookHistory().getDefaultBranch())
          .build();
    }

    String stateType = buildState.getStateType().toString();

    BuildDetailResponseDto buildDetailResponseDto = BuildDetailResponseDto.builder()
        .projectName(buildState.getProject().getProjectName())
        .projectId(buildState.getProject().getId())
        .stateType(StateType.valueOf(stateType))
        .buildNumber(buildState.getBuildNumber())
        .registDate(buildState.getRegistDate())
        .gitInfo(gitInfo)
        .consoleLog(consoleLog.toString())
        .build();

    return buildDetailResponseDto;

  }

  @Override
  public List<ProjectListResponseDto> projectList() {
    log.info("Project List");
    List<Project> projectList = projectRepository.findAll();

    List<ProjectListResponseDto> resultList = new ArrayList<>();

    for (Project project : projectList) {
      ProjectListResponseDto projectListDto = ProjectListResponseDto.from(project);
      resultList.add(projectListDto);
    }

    log.info("project list size {}", resultList.size());
    return resultList;
  }
}
