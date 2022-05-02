package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.core.gitlab.dto.GitlabWebHookDto;
import com.ssafy.dockerby.dto.project.*;
import com.ssafy.dockerby.entity.project.Project;
import javassist.NotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProjectService {

  Optional<Project> findProjectByName(String name);

  ProjectConfigDto findConfigById(Long projectId)
      throws NotFoundException, IOException;

  Map<Project, String> upsert(ProjectConfigDto projectConfigDto)
      throws NotFoundException,IOException;

  void build(Long ProjectId, GitlabWebHookDto webHookDto) throws NotFoundException, IOException;


  List<FrameworkTypeResponseDto> getFrameworkType();

  FrameworkVersionResponseDto getFrameworkVersion(Long typeId)
      throws NotFoundException;

  List<ProjectListResponseDto> projectList() throws NotFoundException, UserDefindedException;

  List<BuildTotalResponseDto> buildTotal(Long projectId)
      throws NotFoundException;

  BuildDetailResponseDto buildDetail(BuildDetailRequestDto buildDetailRequestDto)
      throws IOException, NotFoundException;
}
