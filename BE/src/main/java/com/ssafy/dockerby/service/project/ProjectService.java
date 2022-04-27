package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import com.ssafy.dockerby.core.gitlab.dto.GitlabWebHookDto;
import com.ssafy.dockerby.dto.project.*;
import com.ssafy.dockerby.entity.project.BuildState;
import com.ssafy.dockerby.entity.project.Project;
import javassist.NotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ProjectService {

  Optional<Project> projectByName(String name);

  Map<String, Object> upsert(ProjectRequestDto projectRequestDto)
      throws NotFoundException,IOException;

  BuildState build(Long ProjectId, GitlabWebHookDto webHookDto) throws NotFoundException, IOException;

  StateResponseDto checkState(StateRequestDto stateRequestDto) throws NotFoundException;

  List<FrameworkTypeResponseDto> getFrameworkType();

  FrameworkVersionResponseDto getFrameworkVersion(Long typeId) throws NotFoundException;

  List<ProjectListResponseDto> projectList() throws NotFoundException, UserDefindedException;

  List<BuildTotalResponseDto> buildTotal(Long projectId) throws NotFoundException;

  BuildDetailResponseDto BuildDetail(BuildDetailRequestDto buildDetailRequestDto) throws IOException;
}
