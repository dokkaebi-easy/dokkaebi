package com.ssafy.dockerby.controller.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import com.ssafy.dockerby.dto.project.ProjectResponseDto;
import com.ssafy.dockerby.service.ProjectService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = {"Project"})
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
  private final ProjectService projectService;

  @PostMapping
  public ResponseEntity<ProjectResponseDto> CreateProject(@RequestBody ProjectRequestDto projectRequestDto ) throws IOException, UserDefindedException {
    //요청 로그출력
    log.info("project create request received {} ",projectRequestDto.toString());

    //프로젝트 저장 & response 반환
    ProjectResponseDto projectResponseDto = projectService.createProject(projectRequestDto);

    //TODO : Response 확인
    log.info("project create Success {} ",projectResponseDto.toString());
    return ResponseEntity.ok(projectResponseDto);
  }
}