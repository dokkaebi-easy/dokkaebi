package com.ssafy.dockerby.controller.project;


import com.ssafy.dockerby.core.gitlab.GitlabWrapper;
import com.ssafy.dockerby.core.gitlab.dto.GitlabWebHookDto;
import com.ssafy.dockerby.dto.project.BuildDetailResponseDto;
import com.ssafy.dockerby.dto.project.BuildTotalResponseDto;
import com.ssafy.dockerby.dto.project.ConfigHistoryListResponseDto;
import com.ssafy.dockerby.dto.project.framework.DbTypeResponseDto;
import com.ssafy.dockerby.dto.project.framework.DbVersionResponseDto;
import com.ssafy.dockerby.dto.project.framework.FrameworkTypeResponseDto;
import com.ssafy.dockerby.dto.project.framework.FrameworkVersionResponseDto;
import com.ssafy.dockerby.dto.project.ProjectConfigDto;
import com.ssafy.dockerby.dto.project.ProjectListResponseDto;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.service.framework.SettingConfigService;
import com.ssafy.dockerby.service.project.ProjectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javassist.NotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Project"})
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
  private final ProjectServiceImpl projectService;

  private final SettingConfigService configService;

  @ApiOperation(value = "프로젝트 생성", notes = "프로젝트를 생성한다.")
  @PostMapping
  public ResponseEntity upsertProject(HttpServletRequest request,@Valid @RequestBody ProjectConfigDto projectConfigDto) throws NotFoundException, IOException {
    //요청 로그출력
    log.info("API Request received : projectConfigDto = {} ",projectConfigDto.toString());

    Map<Project, String> upsertResult = projectService.upsert(projectConfigDto);

    //히스토리 저장
    try { //유저가 있을때
      for(Project project : upsertResult.keySet())
        projectService.createConfigHistory(request,project,upsertResult.get(project));
    }catch (Exception e){ // 유저가 없을때 //ex)git hook 상황
      log.error("information does not exist Exception {}",e);
    }

    Map<String, Object> map = new HashMap<>();
    map.put("status", "Success");

    log.info("done : {}", projectConfigDto);
    return ResponseEntity.ok(map);
  }

  @ApiOperation(value = "프로젝트 설정 값", notes = "프로젝트 설정 정보를 반환 한다.")
  @GetMapping("/config/{projectId}")
  public ResponseEntity projectConfig(@PathVariable Long projectId)
      throws NotFoundException, IOException {
    log.info("API Request received : projectId = {} ",projectId);
    return ResponseEntity.ok(projectService.findConfigById(projectId));
  }

  @ApiOperation(value = "프로젝트 빌드", notes = "프로젝트 빌드를 한다.")
  @PostMapping("/build")
  public ResponseEntity buildProject(Long projectId ) throws IOException, NotFoundException {
    log.info("API Request received : projectId = {} ",projectId);
    LocalDateTime startTime = LocalDateTime.now();

    //프로젝트 기본 설정 시작
    projectService.build(projectId, null);
    //build 시작
    projectService.pullStart(projectId, null);
    projectService.buildStart(projectId, null);
    projectService.runStart(projectId, null);

    LocalDateTime endTime = LocalDateTime.now();
    Duration duration =  Duration.between(startTime,endTime);

    return ResponseEntity.ok(projectService.updateProjectDone(projectId,duration));
  }

  @ApiOperation(value = "프레임 워크 타입", notes = "프레임 워크 타입을 반환 해준다.")
  @GetMapping("/frameworkType")
  public ResponseEntity<List<FrameworkTypeResponseDto>> getFrameworkType(){
    log.info("API Request received");

    //type list 입력
    List<FrameworkTypeResponseDto> frameworkTypes = configService.frameworkTypes();

    //type list 반환
    log.info("API Response return");
    return ResponseEntity.ok(frameworkTypes);
  }
  @ApiOperation(value = "프레임 워크 버전", notes = "프레임 워크 타입별 버전을 반환 해준다.")
  @GetMapping("/frameworkVersion")
  public ResponseEntity<FrameworkVersionResponseDto> GetFrameworkVersion(@RequestParam Long typeId) throws NotFoundException {
    //version 요청 로그 출력
    log.info("frameworkVersion API received typeId: {}",typeId);

    return ResponseEntity.ok(configService.frameworkVersion(typeId));
  }

  @ApiOperation(value = "데이터베이스 타입", notes = "데이터베이스 서버 종류를 반환해준다.")
  @GetMapping("/dbType")
  public ResponseEntity<List<DbTypeResponseDto>> dbTypes(){
    //type 요청 로그 출력
    log.info("dbType API received");

    //type list 입력
    List<DbTypeResponseDto> dbTypes = configService.dbTypes();

    //type list 반환
    return ResponseEntity.ok(dbTypes);
  }

  @ApiOperation(value = "데이터베이스 버전 정보", notes = "데이터베이스 버전 정보와 관련 속성을 반환해준다.")
  @GetMapping("/dbVersion")
  public ResponseEntity<DbVersionResponseDto> dbVersions(@RequestParam Long typeId)
      throws NotFoundException, IOException {
    //version 요청 로그 출력
    log.info("API Request received : typeId = {}",typeId);

    log.info("API Response return");
    return ResponseEntity.ok(configService.dbVersion(typeId));
  }


  @ApiOperation(value = "프로젝트 전체 빌드 상황", notes = "프로젝트 전체 빌드 상황을 가져온다.")
  @GetMapping("/build/total")
  public ResponseEntity<List<BuildTotalResponseDto>> buildTotal(Long projectId) throws NotFoundException {
    log.info("API Request received : projectId = {}",projectId);
    // 요청 로그 출력
    log.info("buildTotal API request received {} ", projectId);

    List<BuildTotalResponseDto> buildTotalResponseDtos = projectService.buildTotal(projectId);

    log.info("API Response return");
    return ResponseEntity.ok(buildTotalResponseDtos);
  }

  @ApiOperation(value = "프로젝트 상세", notes = "프로젝트 상세 내역을 가져온다.")
  @GetMapping("/build/detail")
  public ResponseEntity<BuildDetailResponseDto> buildDetail(Long buildStateId) throws NotFoundException {
    log.info("API Request received : buildStateId = {}",buildStateId);
    //요청 로그 출력
    log.info("buildDetail API request received {}",buildStateId);

    //프로젝트 state 저장 stateResponse 반환
    BuildDetailResponseDto buildDetailResponseDto = projectService.buildDetail(buildStateId);

    log.info("API Response return");
    return ResponseEntity.ok(buildDetailResponseDto);
  }

  @ApiOperation(value = "프로젝트 목록", notes = "프로젝트 목록을 가져온다.")
  @GetMapping("/all")
  public ResponseEntity<List<ProjectListResponseDto>> projects(){
    log.info("API Request received");

    List<ProjectListResponseDto> projectList = projectService.projectList();

    log.info("API Response return");
    return ResponseEntity.ok(projectList);
  }

  @ApiOperation(value = "ConfigHistory 리스트", notes = "ConfigHistory 목록을 가져온다.")
  @GetMapping("/configHistory")
  public ResponseEntity<List<ConfigHistoryListResponseDto>> configHistory() {
    log.info("API Request received");

    List<ConfigHistoryListResponseDto> configHistoryList = projectService.historyList();

    log.info("API Response return");
    return ResponseEntity.ok(configHistoryList);
  }

  @PostMapping("/hook/{projectName}")
  public ResponseEntity webHook(@PathVariable String projectName,
      @RequestHeader(name = "X-Gitlab-Token") String token,
      @RequestBody Map<String, Object> params) throws NotFoundException, IOException {
    log.info("API Request received : projectName = {}",projectName);
    LocalDateTime startTime=LocalDateTime.now();
    GitlabWebHookDto webHookDto = GitlabWrapper.wrap(params);

    Project project = projectService.findProjectByName(projectName)
        .orElseThrow(() -> new NotFoundException("Webhook projectName : "+projectName));

    if(!token.equals(project.getGitConfig().getSecretToken()))
      throw new IllegalArgumentException("Unauthorized secret token "+token);

    log.debug("ProjectController.Webhook : X-Gitlab-Toke : {} / " , token,params);

    //프로젝트 기본 설정 시작
    projectService.build(project.getId(), webHookDto);
    projectService.pullStart(project.getId(), webHookDto);
    projectService.buildStart(project.getId(), webHookDto);
    projectService.runStart(project.getId(), webHookDto);

    LocalDateTime endTime=LocalDateTime.now();

    Duration duration =Duration.between(startTime,endTime);
    log.info("API Response null");
    return ResponseEntity.ok(projectService.updateProjectDone(project.getId(),duration));
  }
}