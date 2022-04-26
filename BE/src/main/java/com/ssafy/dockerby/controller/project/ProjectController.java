package com.ssafy.dockerby.controller.project;

import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import com.ssafy.dockerby.dto.project.BuildTotalResponseDto;
import com.ssafy.dockerby.dto.project.ConfigHistoryListResponseDto;
import com.ssafy.dockerby.dto.project.FrameworkTypeResponseDto;
import com.ssafy.dockerby.dto.project.FrameworkVersionResponseDto;
import com.ssafy.dockerby.dto.project.ProjectListResponseDto;
import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import com.ssafy.dockerby.dto.project.StateRequestDto;
import com.ssafy.dockerby.dto.project.StateResponseDto;
import com.ssafy.dockerby.dto.user.UserDetailDto;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.service.project.ProjectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Project"})
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
  private final ProjectServiceImpl projectService;

  @PostMapping
  public ResponseEntity createProject(HttpServletRequest request,@RequestBody ProjectRequestDto projectRequestDto ) throws ChangeSetPersister.NotFoundException {
    //요청 로그출력
    log.info("project create request");
    log.info("Request Project : {} User : {}",projectRequestDto.getProjectName());
    Map<String, Object> upsertResult = projectService.upsert(projectRequestDto);

    List<DockerContainerConfig> configs = (List<DockerContainerConfig>) upsertResult.get("buildConfigs");
    Project project = (Project) upsertResult.get("project");
    String msg = (String) upsertResult.get("msg");
    log.info("project build start / waiting -> processing");

    //히스토리 저장
    try { //유저가 있을때
      projectService.createConfigHistory(request,project,msg);
    }catch (Exception e){ // 유저가 없을때 //ex)git hook 상황
      log.error("User information does not exist Exception {} {}",e.getClass(),e.getMessage());
    }

    Map<String, Object> map = new HashMap<>();
    map.put("status", "Success");

    log.info("PrjoectController.createProject success : {}", projectRequestDto);
    return ResponseEntity.ok(map);
  }

  @PostMapping("/build")
  public ResponseEntity buildProject(Long projectId ) throws IOException, ChangeSetPersister.NotFoundException {
    log.info("buildProject request API received , id : {}",projectId);

    //프로젝트 빌드 시작
    projectService.build(projectId);

    return ResponseEntity.ok(null);
  }



  @PatchMapping
  public ResponseEntity updateProject(ProjectRequestDto projectRequestDto) {
    log.info("PrjoectController.updateProject input : {}", projectRequestDto);

    // TODO

    Map<String, Object> map = new HashMap<>();
    map.put("status", "Success");

    log.info("PrjoectController.updateProject success : {}", projectRequestDto);
    return ResponseEntity.ok(map);
  }

  @GetMapping("/frameworkType")
  public ResponseEntity<List<FrameworkTypeResponseDto>> getFrameworkType(){
    //type 요청 로그 출력
    log.info("frameworkType API received");

    //type list 입력
    List<FrameworkTypeResponseDto> frameworkTypes = projectService.getFrameworkType();

    //type list 반환
    return ResponseEntity.ok(frameworkTypes);
  }

  @GetMapping("/frameworkVersion")
  public ResponseEntity<FrameworkVersionResponseDto> GetFrameworkVersion(Long typeId) throws ChangeSetPersister.NotFoundException {
    //version 요청 로그 출력
    log.info("frameworkVersion API received typeId: {}",typeId);

    return ResponseEntity.ok(projectService.getFrameworkVersion(typeId));
  }

  @GetMapping("/build/Detail")
  public ResponseEntity<StateResponseDto> buildState(StateRequestDto stateRequestDto ) throws ChangeSetPersister.NotFoundException {
    //요청 로그 출력
    log.info("buildDetail request received {}",stateRequestDto.toString());

    //프로젝트 state 저장 stateResponse 반환
    StateResponseDto stateResponseDto = projectService.checkState(stateRequestDto);

    return ResponseEntity.ok(stateResponseDto);
  }
  @GetMapping("/build/total")
  public ResponseEntity<List<BuildTotalResponseDto>> buildTotal(Long projectId) throws ChangeSetPersister.NotFoundException {
    // 요청 로그 출력
    log.info("buildTotal API request received {} ", projectId);

    List<BuildTotalResponseDto> buildTotalResponseDtos = projectService.buildTotal(projectId);

    return ResponseEntity.ok(buildTotalResponseDtos);
  }

  @ApiOperation(value = "프로젝트 목록", notes = "프로젝트 목록을 가져온다")
  @GetMapping("/all")
  public ResponseEntity<List<ProjectListResponseDto>> projects(){
    log.info("Project all API received");

    List<ProjectListResponseDto> projectList = projectService.projectList();
    return ResponseEntity.ok(projectList);
  }

  @ApiOperation(value = "ConfigHistory 리스트", notes = "ConfigHistory 목록을 가져온다")
  @GetMapping("/confighistory")
  public ResponseEntity<List<ConfigHistoryListResponseDto>> confighistory() {
    log.info("confighistory API received");

    List<ConfigHistoryListResponseDto> configHistoryList = projectService.historyList();
    return ResponseEntity.ok(configHistoryList);
  }

}