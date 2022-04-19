package com.ssafy.dockerby.controller.project;

import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import com.ssafy.dockerby.dto.project.ProjectResponseDto;
import com.ssafy.dockerby.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
  private final ProjectService projectService;

  @PostMapping
  public ResponseEntity<ProjectResponseDto> CreateProject(@RequestBody ProjectRequestDto projectRequestDto ) throws IOException {
    log.info("프로젝트 생성 요청 들어옴");
    return ResponseEntity.ok(projectService.createProject(projectRequestDto));
  }

}