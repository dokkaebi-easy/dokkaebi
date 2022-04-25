package com.ssafy.dockerby.controller.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.project.*;
import com.ssafy.dockerby.entity.project.ProjectState;
import com.ssafy.dockerby.service.project.ProjectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api(tags = {"Project"})
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
  private final ProjectServiceImpl projectService;

  @PostMapping
  public ResponseEntity<ProjectResponseDto> createProject(Principal principal,ProjectRequestDto projectRequestDto ) throws IOException, UserDefindedException, ChangeSetPersister.NotFoundException {
    //요청 로그출력
    log.info("project create request received {} ",projectRequestDto.toString());
    log.info("username{}",principal.getName());
    //프로젝트 저장 & response 반환
    ProjectResponseDto projectResponseDto = projectService.createProject(principal,projectRequestDto);

    log.info("project build start / waiting -> processing");
    //프로젝트 buildStart
    ProjectState projectState = projectService.build(projectRequestDto);

    //TODO : Response 확인
    log.info("project create Success {} {}", projectResponseDto.toString(), projectState.toString());
    return ResponseEntity.ok(projectResponseDto);
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
  public ResponseEntity<List<FrameworkVersionResponseDto>> GetFrameworkVersion(Long typeId) throws ChangeSetPersister.NotFoundException {
    //version 요청 로그 출력
    log.info("frameworkVersion API received typeId: {}",typeId);

    //version list 입력
    List<FrameworkVersionResponseDto> frameworkVersionResponses = projectService.getFrameworkVersion(typeId);

    //version list 반환
    return ResponseEntity.ok(frameworkVersionResponses);
  }

  @GetMapping("/build/Detail")
  public ResponseEntity<StateResponseDto> projectState(StateRequestDto stateRequestDto ) throws ChangeSetPersister.NotFoundException {
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
  public ResponseEntity<List<ProjectListDto>> projects(){
    log.info("Project all API received");

    List<ProjectListDto> projectList = projectService.projectList();
    return ResponseEntity.ok(projectList);
  }

  @ApiOperation(value = "ConfigHistory 리스트", notes = "ConfigHistory 목록을 가져온다")
  @GetMapping("/confighistory")
  public ResponseEntity<List<ConfigHistoryListDto>> confighistory() {
    log.info("confighistory API received");

    List<ConfigHistoryListDto> configHistoryList = projectService.historyList();
    return ResponseEntity.ok(configHistoryList);
  }

}