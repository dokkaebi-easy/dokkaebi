package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.core.docker.DockerAdapter;
import com.ssafy.dockerby.core.docker.EtcConfigMaker;
import com.ssafy.dockerby.core.docker.vo.docker.BuildConfig;
import com.ssafy.dockerby.core.docker.vo.docker.DbConfig;
import com.ssafy.dockerby.core.docker.vo.docker.DockerbyProperty;
import com.ssafy.dockerby.core.docker.vo.nginx.NginxConfig;
import com.ssafy.dockerby.core.docker.vo.nginx.NginxHttpsOption;
import com.ssafy.dockerby.core.gitlab.GitlabAdapter;
import com.ssafy.dockerby.core.gitlab.dto.GitlabCloneDto;
import com.ssafy.dockerby.core.gitlab.dto.GitlabWebHookDto;
import com.ssafy.dockerby.core.util.CommandInterpreter;
import com.ssafy.dockerby.dto.project.BuildConfigDto;
import com.ssafy.dockerby.dto.project.BuildDetailResponseDto;
import com.ssafy.dockerby.dto.project.BuildTotalDetailDto;
import com.ssafy.dockerby.dto.project.BuildTotalResponseDto;
import com.ssafy.dockerby.dto.project.ConfigHistoryListResponseDto;
import com.ssafy.dockerby.dto.project.ConfigProperty;
import com.ssafy.dockerby.dto.project.DBConfigDto;
import com.ssafy.dockerby.dto.project.GitConfigDto;
import com.ssafy.dockerby.dto.project.NginxConfigDto;
import com.ssafy.dockerby.dto.project.ProjectConfigDto;
import com.ssafy.dockerby.dto.project.ProjectListResponseDto;
import com.ssafy.dockerby.dto.project.framework.FrameworkTypeResponseDto;
import com.ssafy.dockerby.dto.project.framework.FrameworkVersionResponseDto;
import com.ssafy.dockerby.dto.user.UserDetailDto;
import com.ssafy.dockerby.entity.ConfigHistory;
import com.ssafy.dockerby.entity.core.FrameworkType;
import com.ssafy.dockerby.entity.core.Version;
import com.ssafy.dockerby.entity.git.GitlabAccessToken;
import com.ssafy.dockerby.entity.git.WebhookHistory;
import com.ssafy.dockerby.entity.project.BuildState;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.entity.project.enums.BuildType;
import com.ssafy.dockerby.entity.project.enums.StateType;
import com.ssafy.dockerby.entity.user.User;
import com.ssafy.dockerby.repository.project.BuildStateRepository;
import com.ssafy.dockerby.repository.project.ConfigHistoryRepository;
import com.ssafy.dockerby.repository.project.FrameworkTypeRepository;
import com.ssafy.dockerby.repository.project.ProjectRepository;
import com.ssafy.dockerby.repository.user.UserRepository;
import com.ssafy.dockerby.service.git.GitlabService;
import com.ssafy.dockerby.util.DockerConfigParser;
import com.ssafy.dockerby.util.FileManager;
import com.ssafy.dockerby.util.PathParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javassist.NotFoundException;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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

  private final PathParser pathParser;

  private final DockerConfigParser dockerConfigParser;

  @Override
  public Optional<Project> findProjectByName(String name) {
    log.info("findProjectByName Start : projectName = {} ",name);
    return projectRepository.findOneByProjectName(name);
  }

  @Override
  public ProjectConfigDto findConfigById(Long projectId) throws NotFoundException, IOException {
    log.info("findConfigById Start : projectID = {} ",projectId);

    Project project = projectRepository.findById(projectId)
        .orElseThrow(
            () -> new NotFoundException("ProjectServiceImpl.configByProjectName : " + projectId));

    String configPath = pathParser.configPath(project.getProjectName()).toString();

    List<BuildConfig> buildConfigs = new ArrayList<>();
    NginxConfigDto nginxConfig = new NginxConfigDto(new ArrayList<>(), new ArrayList<>(), false,
        new NginxHttpsOption("", "", ""));
    List<DbConfig> dbConfigs = new ArrayList<>();
    GitConfigDto gitConfigDto = GitConfigDto.from(gitlabService.config(projectId)
        .orElseThrow(() -> new NotFoundException("gitlab config not found")));

    File configDirectory = new File(configPath);
    for (String fileName : configDirectory.list()) {
      if ("build".equals(fileName)) {
        buildConfigs = FileManager.loadJsonFileToList(configPath, "build", BuildConfig.class);
      } else if ("db".equals(fileName)) {
        dbConfigs = FileManager.loadJsonFileToList(configPath, "db", DbConfig.class);
      } else if ("nginx".equals(fileName)) {
        nginxConfig = NginxConfigDto.from(
            FileManager.loadJsonFile(configPath, "nginx", NginxConfig.class));
      }
    }

    List<BuildConfigDto> buildConfigDtos = new ArrayList<>();
    for (BuildConfig buildConfig : buildConfigs) {
      FrameworkType framework = frameworkTypeRepository.findByFrameworkName(
          buildConfig.getFramework()).orElseThrow();
      Version version = framework.getLanguage()
          .findVersionByDocker(buildConfig.getVersion())
          .orElseThrow(() -> new IllegalArgumentException("Version miss match"));
      buildConfigDtos.add(
          BuildConfigDto.builder()
              .frameworkId(framework.getId())
              .name(buildConfig.getName())
              .projectDirectory(buildConfig.getProjectDirectory())
              .buildPath(buildConfig.getBuildPath())
              .version(version.getInputVersion())
              .type(buildConfig.getType())
              .properties(dockerConfigParser.configProperties(buildConfig.getProperties()))
              .build());
    }

    List<DBConfigDto> dbConfigDtos = new ArrayList<>();
    for (DbConfig config : dbConfigs) {
      FrameworkType framework = frameworkTypeRepository.findByFrameworkName(
          config.getFramework()).orElseThrow();
      Version version = framework.getLanguage().findVersionByDocker(config.getVersion())
          .orElseThrow(() -> new IllegalArgumentException("dbconfig version miss match"));
      dbConfigDtos.add(
          DBConfigDto.builder()
              .name(config.getName())
              .dumpLocation(config.getDumpLocation())
              .frameworkId(framework.getId())
              .version(version.getInputVersion())
              .properties(dockerConfigParser.configProperties(config.getProperties()))
              .build());
    }

    return ProjectConfigDto.of(projectId, project.getProjectName(), buildConfigDtos, gitConfigDto,
        nginxConfig, dbConfigDtos);
  }

  @Override
  public Map<Project, String> upsert(ProjectConfigDto projectConfigDto)
      throws NotFoundException, IOException {
    log.info("upsert Start : projectConfigDto = {} ",projectConfigDto.getProjectName());

    Map<Project, String> result = new HashMap<>();
    Project project;
    if (projectConfigDto.getProjectId() != 0) {
      project = projectRepository.findById(projectConfigDto.getProjectId())
          .orElseThrow(() -> new NotFoundException(
              "upsert user not found id " + projectConfigDto.getProjectId()));
      //프로젝트 Id가 있을 시 Update
      log.info("upsert : projectUpdate = {} ",project.getProjectName());
      result.put(project, "update");
    } else {
      project = projectRepository.save(Project.from(projectConfigDto));
      //프로젝트 Id가 없을 시 Create
      log.info("upsert : projectCreate = {} ",project.getProjectName());
      result.put(project, "create");
    }

    String projectPath = pathParser.projectPath(projectConfigDto.getProjectName()).toString();
    String logPath = pathParser.logPath(projectConfigDto.getProjectName()).toString();
    String configPath = pathParser.configPath(projectConfigDto.getProjectName()).toString();
    String repositoryPath = pathParser.repositoryPath(projectConfigDto.getProjectName(),
        projectConfigDto.getGitConfig().getGitProjectId()).toString();

    // config, git clone 지우고 다시 저장

    FileUtils.deleteDirectory(new File(configPath));
    FileUtils.deleteDirectory(new File(repositoryPath));

    // 빌드 환경설정 Convert
    List<BuildConfig> buildConfigs = new ArrayList<>();
    for (BuildConfigDto buildConfigDto : projectConfigDto.getBuildConfigs()) {
      FrameworkType framework = frameworkTypeRepository.findById(
          buildConfigDto.getFrameworkId()).orElseThrow();
      Version version = framework.getLanguage().findVersionByInput(buildConfigDto.getVersion())
          .orElseThrow(() -> new IllegalArgumentException(buildConfigDto.getVersion()));
      buildConfigs.add(
          dockerConfigParser.buildConverter(buildConfigDto.getName(), framework.getFrameworkName(),
              version.getDockerVersion(),
              dockerConfigParser.dockerbyProperties(buildConfigDto.getProperties()),
              buildConfigDto.getProjectDirectory(), buildConfigDto.getBuildPath(),
              buildConfigDto.getType()));
    }

    // 빌드 환경설정 파일 저장
    FileManager.saveJsonFile(configPath, "build", buildConfigs);

    // Git cofig upsert
    log.info("GitConfigDto project ID : {}", project.getId());
    GitConfigDto getConfigDto = projectConfigDto.getGitConfig();
    gitlabService.config(project.getId())
        .map(config -> gitlabService.updateConfig(project, getConfigDto))
        .orElseGet(() -> gitlabService.createConfig(project, getConfigDto));

    // git clone
    log.info("upsert : GitClone Start");
    GitlabAccessToken token = gitlabService.token(getConfigDto.getAccessTokenId());

    String cloneCommand = GitlabAdapter.getCloneCommand(
        GitlabCloneDto.of(token.getAccessToken(), getConfigDto.getRepositoryUrl(),
            getConfigDto.getBranchName(), getConfigDto.getGitProjectId()));

    CommandInterpreter.runDestPath(projectPath, logPath, "Clone", 0, cloneCommand);

    DockerAdapter dockerAdapter = new DockerAdapter(repositoryPath,
        projectConfigDto.getProjectName());

    // dockerfile save
    try {
      dockerAdapter.saveDockerfiles(buildConfigs);
    } catch (Exception e) {
      log.error("docker file not making {} DockerAdapter({})", project.getProjectName());
    }

    // NGINX config
    NginxConfig nginxConfig = dockerConfigParser.nginxConverter(projectConfigDto.getNginxConfig());
    if (!nginxConfig.isEmpty()) {
      String defaultConfPath = "";
      for (BuildConfig buildConfig : buildConfigs) {
        if (buildConfig.useNginx()) {
          defaultConfPath = buildConfig.getProjectDirectory();
          buildConfig.addProperty(new DockerbyProperty("publish", "80", "80"));
          if (nginxConfig.isHttps()) {
            buildConfig.addProperty(new DockerbyProperty("publish", "443", "443"));
          }
          break;
        }
      }

      if (defaultConfPath.isEmpty()) {
        throw new IllegalArgumentException("NGINX ERROR");
      }

      EtcConfigMaker.nginxConfig(
          pathParser.configPath(projectConfigDto.getProjectName()).append("/")
              .append(defaultConfPath).toString(), nginxConfig);
      EtcConfigMaker.saveDockerNginxConfig(configPath, nginxConfig);
    }

    // DB condig
    List<DbConfig> dbConfigs = new ArrayList<>();
    for (DBConfigDto dbConfigDto : projectConfigDto.getDbConfigs()) {
      if (dbConfigDto.getName().isBlank()) {
        continue;
      }
      if (dbConfigDto.getFrameworkId() == -1) {
        continue;
      }
      if (dbConfigDto.getPort().isBlank()) {
        continue;
      }
      if (dbConfigDto.getVersion().isBlank()) {
        continue;
      }
      FrameworkType framework = frameworkTypeRepository.findById(
          dbConfigDto.getFrameworkId()).orElseThrow();
      Version version = framework.getLanguage().findVersionByInput(dbConfigDto.getVersion())
          .orElseThrow(() -> new IllegalArgumentException("DB CONFIG VERSION ERROR"));

      List<DockerbyProperty> list = new ArrayList<>();
      for (ConfigProperty property : dbConfigDto.getProperties()) {
        if (property.checkEmpty()) {
          continue;
        }
        if (property.getProperty().equals("publish")) {
          list.add(new DockerbyProperty("publish", property.getData(), property.getData()));
        } else {
          list.add(new DockerbyProperty("environment", property.getProperty(), property.getData()));
        }
      }
      dbConfigs.add(
          dockerConfigParser.DbConverter(dbConfigDto.getName(), framework.getFrameworkName(),
              version.getDockerVersion(), list, dbConfigDto.getDumpLocation(),
              project.getProjectName()));
    }
    if (!dbConfigs.isEmpty()) {
      FileManager.saveJsonFile(configPath, "db", dbConfigs);
    }

    log.info("loadConfigFilesByFileName Done");
    return result;
  }

  private void createBuildState(Project project, GitlabWebHookDto webHookDto) {
    log.info("createBuildState Start : project.getName = {} ",project.getProjectName());

    List<BuildState> buildStates = new ArrayList<>();
    Long buildNumber = Long.valueOf(
        buildStateRepository.findAllByProjectIdOrderByBuildNumberDesc(project.getId()).size() / 3);

    BuildState buildState = BuildState.builder()
        .project(project)
        .buildNumber(buildNumber)
        .buildType(BuildType.valueOf("Pull"))
        .stateType(StateType.valueOf("Processing"))
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
    log.info("createBuildState Done");
    em.flush();
  }

  @Override
  public boolean projectIsFailed(Long projectId) throws NotFoundException {
    log.info("projectIsFailed Start : projectId = {} ",projectId);

    Project project = projectRepository.findById(projectId)
        .orElseThrow(
            () -> new NotFoundException("ProjectSerivceImpl.projectIsFailed : " + projectId));
    //프로젝트가 실패상태이면 ture 반환
    if("Failed".equals(project.getStateType().toString())) {
      log.info("projectIsFailed : return true");
      return true;
    }
    else {
      log.info("projectIsFailed : return false");
      return false;
    }
  }

  @Override
  public void build(Long projectId, GitlabWebHookDto webHookDto)
    throws NotFoundException, IOException {
    log.info("build Start : projectId = {} ",projectId);

    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new NotFoundException("ProjectSerivceImpl.build : " + projectId));

    //프로젝트 상태 진행중으로 변경
    project.updateState(StateType.Processing);

    log.info("build : updateState project.getStateType = {} ",project.getStateType());

    createBuildState(project, webHookDto);

    log.info("build Done");
    em.flush();
  }


  @Override
  public void pullStart(Long projectId, GitlabWebHookDto webHookDto)
      throws NotFoundException, IOException {
    log.info("pullStart Start : projectId = {} ",projectId);

    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new NotFoundException("ProjectServiceImpl.pullStart : " + projectId));

    List<BuildState> buildStates = buildStateRepository.findAllByProjectIdOrderByBuildNumberDesc(
        projectId);

    String logPath = pathParser.logPath(project.getProjectName()).toString();
    String repositoryPath = pathParser.repositoryPath(project.getProjectName(),
            project.getGitConfig().getGitProjectId())
        .toString();

    int buildNumber = Integer.parseInt(buildStates.get(0).getBuildNumber().toString());

    //Pull Start
    try { // pull 트라이
      if (buildStates.get(2).getWebhookHistory() != null) {
        List<String> commands = new ArrayList<>();
        commands.add(GitlabAdapter.getPullCommand(webHookDto.getDefaultBranch()));
        CommandInterpreter.runDestPath(repositoryPath, logPath, "Pull", buildNumber, commands);
      }
      // pull 완료 build 진행중 update
      buildStates.get(2).updateStateType("Done");
      buildStates.get(1).updateStateType("Processing");

      log.info("pullStart : Pull Success : {}", buildStates.get(0).toString());

      //dirtyCheck 후 flush
      em.flush();

    } catch (Exception e) { // state failed 넣기
      //pullState failed 입력
      buildStates.get(2).updateStateType("Failed");
      project.updateState(StateType.Failed);

      log.info("pullStart : update state to Failed");

      em.flush();

      //에러 로그 출력
      log.error("pullStart : Pull failed {}", e);
    }

    log.info("pullStart Done");
    em.flush();
  }

  @Override
  public void buildStart(Long projectId, GitlabWebHookDto webHookDto)
      throws NotFoundException, IOException {
    log.info("buildStart Start : projectId = {} ",projectId);
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new NotFoundException("ProjectServiceImpl.buildStart : " + projectId));

    String logPath = pathParser.logPath(project.getProjectName()).toString();
    String configPath = pathParser.configPath(project.getProjectName()).toString();
    String repositoryPath = pathParser.repositoryPath(project.getProjectName(),
            project.getGitConfig().getGitProjectId())
        .toString();

    DockerAdapter dockerAdapter = new DockerAdapter(repositoryPath, project.getProjectName());

    List<BuildConfig> buildConfigs = FileManager.loadJsonFileToList(configPath, "build",
        BuildConfig.class);

    List<BuildState> buildStates = buildStateRepository.findAllByProjectIdOrderByBuildNumberDesc(
        projectId);

    int buildNumber = Math.toIntExact(buildStates.get(0).getBuildNumber());

    try { // Build 트라이
      List<String> buildCommands = dockerAdapter.getBuildCommands(buildConfigs);
      CommandInterpreter.run(logPath, "Build", (buildNumber), buildCommands);

      // state Done 넣기
      buildStates.get(1).updateStateType("Done");
      buildStates.get(0).updateStateType("Processing");

      //dirtyCheck 후 flush
      em.flush();

      //성공 로그 출력
      log.info("buildStart : Build Success : {}", buildStates.get(1).toString());
    } catch (Exception e) { // state failed 넣기
      //buildState failed 입력
      buildStates.get(1).updateStateType("Failed");
      project.updateState(StateType.Failed);

      log.error("buildStart : Build Failed {} ", e);

      em.flush();
    }
    log.info("buildStart Done");
  }

  @Override
  public void runStart(Long projectId, GitlabWebHookDto webHookDto)
      throws NotFoundException, IOException {
    log.info("runStart Start: projectId = {} ",projectId);

    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new NotFoundException("ProjectServiceImpl.runStart : " + projectId));

    String logPath = pathParser.logPath(project.getProjectName()).toString();
    String configPath = pathParser.configPath(project.getProjectName()).toString();
    String repositoryPath = pathParser.repositoryPath(project.getProjectName(),
            project.getGitConfig().getGitProjectId())
        .toString();

    DockerAdapter dockerAdapter = new DockerAdapter(repositoryPath, project.getProjectName());

    List<BuildConfig> buildConfigs = new ArrayList<>();

    List<DbConfig> dbConfigs = new ArrayList<>();

    File configDirectory = new File(configPath);
    for (String fileName : configDirectory.list()) {
      if ("build".equals(fileName)) {
        buildConfigs = FileManager.loadJsonFileToList(configPath, "build", BuildConfig.class);
      } else if ("db".equals(fileName)) {
        dbConfigs = FileManager.loadJsonFileToList(configPath, "db", DbConfig.class);
      }
    }

    List<BuildState> buildStates = buildStateRepository.findAllByProjectIdOrderByBuildNumberDesc(
        projectId);

    int buildNumber = Math.toIntExact(buildStates.get(0).getBuildNumber());
    try { // run 트라이
      if (buildNumber != 1) {
        if (!dbConfigs.isEmpty()) {
          CommandInterpreter.run(logPath, "Remove", buildNumber,
              dockerAdapter.getRemoveCommands(dbConfigs));
        }
        if (!buildConfigs.isEmpty()) {
          CommandInterpreter.run(logPath, "Remove", buildNumber,
              dockerAdapter.getRemoveCommands(buildConfigs));
        }
      }
      List<String> commands = new ArrayList<>();
      if (!dbConfigs.isEmpty()) {
        commands.addAll(dockerAdapter.getRunCommands(dbConfigs));
      }
      if (!buildConfigs.isEmpty()) {
        commands.addAll(dockerAdapter.getRunCommands(buildConfigs));
      }
      CommandInterpreter.run(logPath, "Run", buildNumber, commands);
      // state Done 넣기
      buildStates.get(0).updateStateType("Done");

      log.info("runStart : Run Success = {} ", buildStates.get(2).toString());

      em.flush();
    } catch (Exception e) { // state failed 넣기
      //dockerRunState failed 입력
      buildStates.get(0).updateStateType("Failed");
      project.updateState(StateType.Failed);

      //dirtyCheck 후 flush
      em.flush();

      //에러 로그 출력
      log.error("runStart : Run Failed {}", e);
    }
    log.info("runStart Done");
  }

  @Override
  public void updateProjectDone(Long projectId) throws NotFoundException {
    log.info("updateProjectDone Start : projectId = {} ",projectId);
    Project project = projectRepository.findById(projectId)
        .orElseThrow(
            () -> new NotFoundException("ProjectSerivceImpl.updateProjectDone : " + projectId));

    project.updateState(StateType.valueOf("Done"));

    log.info("updateProject Done : projectId = {} ",project.getStateType());
    em.flush();
  }

  @Override
  public List<FrameworkTypeResponseDto> getFrameworkType() {
    log.info("getFrameworkType Start");

    //frameworkTypes initialized
    List<FrameworkTypeResponseDto> frameworkTypes = new ArrayList<>();

    try {
      // 모든 FrameworkType을 조회한 뒤 리스트에 담아서 반환
      frameworkTypeRepository.findAll().forEach(value ->
          frameworkTypes.add(FrameworkTypeResponseDto.from(value)));

      //성공 로그 출력
      log.info("getFrameworkType Success");
    } catch (Exception error) {
      //실패 로그 출력
      log.error("getFrameworkType Failed {}", error);

      throw error;
    }
    log.info("getFrameworkType Done : typeSize = {}", frameworkTypes.size());
    return frameworkTypes;
  }

  @Override
  public FrameworkVersionResponseDto getFrameworkVersion(Long typeId) throws NotFoundException {
    log.info("getFrameworkVersion Start : typeId = {}",typeId);

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
      log.info("getFrameworkVersion Success");
      return FrameworkVersionResponseDto.from(type.getLanguage().getName(), versions, buildTools);
    } catch (Exception error) {
      //실패 로그 출력
      log.error("getFrameworkVersion Failed {}", error);
      throw error;
    }
  }


  //history 저장
  public void createConfigHistory(HttpServletRequest request, Project project, String msg)
      throws NotFoundException {
    log.info("createConfigHistory Start : projectName = {} ",project.getProjectName());
    //세션 정보 가져오기
    HttpSession session = request.getSession();
    UserDetailDto userDetailDto = (UserDetailDto) session.getAttribute("user");
    log.info("session User Principal : {} ", userDetailDto.getUsername());

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

    log.info("history save {} to {} detail-{}", history.getUser().getName(),
      history.getProject().getProjectName(), history.getMsg());

    configHistoryRepository.save(history);
    log.info("createConfigHistory Done");
  }

  public List<ConfigHistoryListResponseDto> historyList() {
    log.info("historyList Start");
    List<ConfigHistory> configHistories = configHistoryRepository.findAll(
        Sort.by(Sort.Direction.DESC, "registDate"));
    List<ConfigHistoryListResponseDto> resultList = new ArrayList<>();

    for (ConfigHistory configHistory : configHistories) {
      ConfigHistoryListResponseDto configHistoryListDto = ConfigHistoryListResponseDto.from(
          configHistory);
      resultList.add(configHistoryListDto);
    }

    log.info("historyList Done : listSize = {}", resultList.size());
    return resultList;
  }

  @Override
  public List<BuildTotalResponseDto> buildTotal(Long projectId) throws NotFoundException {
    log.info("buildTotal Start");

    //responseDtos initialized
    List<BuildTotalResponseDto> responseDtos = new ArrayList<>();
    List<BuildTotalDetailDto> buildTotalDetailDtos = new ArrayList<>();

    //해당 projectId의 buildState List로 받음
    List<BuildState> buildStates = buildStateRepository.findAllByProjectIdOrderByBuildNumberAsc(
        projectId); // sort따로 지정하기 라스트 보장x

    //입력 시작 로그 출력
    log.info("buildState insert Start  buildStateSize : {}", buildStates.size());

    int counter = 1;

    //각각의 buildState에 대해 추출후 입력
    for (BuildState buildState : buildStates) {

      BuildTotalDetailDto buildTotalDetailDto = BuildTotalDetailDto.builder()
          .buildStateId(buildState.getId())
          .buildNumber(buildState.getBuildNumber())
          .buildType(buildState.getBuildType())
          .stateType(buildState.getStateType())
          .registDate(buildState.getRegistDate())
          .lastModifiedDate(buildState.getLastModifiedDate())
          .build();

      buildTotalDetailDtos.add(buildTotalDetailDto);

      if (counter == 3) { // 3개씩 List 에 담아주기
        BuildTotalResponseDto buildTotalResponseDto = BuildTotalResponseDto.builder()
            .buildNumber(buildState.getBuildNumber())
            .buildTotalDetailDtos(buildTotalDetailDtos)
            .build();

        //완성된 buildTotalResponseDto를 저장
        responseDtos.add(buildTotalResponseDto);
        counter = 0;
        buildTotalDetailDtos = new ArrayList<>();
      }
      counter++;
    }
    log.info("buildTotal Done : responseSize = {}", responseDtos.size());
    return responseDtos;
  }

  @Override
  public BuildDetailResponseDto buildDetail(Long buildStateId)
      throws NotFoundException {
    log.info("buildDetail Start");

    BuildState buildState = buildStateRepository.findById(buildStateId)
        .orElseThrow(() -> new NotFoundException(
            "ProjectServiceImpl.buildDetail : Not found build state "
                + buildStateId));

    log.info("buildDetail : receive success {}", buildState.getProject().getProjectName());

    String logPath = pathParser.logPath(buildState.getProject().getProjectName()).toString();

    String fileName =
        (buildState.getBuildType().toString()) + "_"
            + buildState.getBuildNumber();//  상태_빌드 넘버

    StringBuilder consoleLog = new StringBuilder();

    log.info("buildDetail : lodeFile Start");
    try {
      consoleLog.append(FileManager.loadFile(logPath, fileName));
      log.info("buildDetail : lodeFile Success");
    } catch (Exception error) {
      log.error("buildDetail : There is no console file : {}",error);
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

    log.info("buildDetail Done");
    return buildDetailResponseDto;
  }

  @Override
  public List<ProjectListResponseDto> projectList() {
    log.info("ProjectList Start");
    List<Project> projectList = projectRepository.findAll();

    List<ProjectListResponseDto> resultList = new ArrayList<>();

    for (Project project : projectList) {
      ProjectListResponseDto projectListDto = ProjectListResponseDto.from(project);
      resultList.add(projectListDto);
    }

    log.info("ProjectList Done : ListSize {}", resultList.size());
    return resultList;
  }
}
