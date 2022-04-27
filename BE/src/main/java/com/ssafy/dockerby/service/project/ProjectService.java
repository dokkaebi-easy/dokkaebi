package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import com.ssafy.dockerby.core.gitlab.dto.GitlabWebHookDto;
import com.ssafy.dockerby.dto.project.BuildTotalResponseDto;
import com.ssafy.dockerby.dto.project.FrameworkTypeResponseDto;
import com.ssafy.dockerby.dto.project.FrameworkVersionResponseDto;
import com.ssafy.dockerby.dto.project.ProjectListResponseDto;
import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import com.ssafy.dockerby.dto.project.StateRequestDto;
import com.ssafy.dockerby.dto.project.StateResponseDto;
import com.ssafy.dockerby.entity.project.BuildState;
import com.ssafy.dockerby.entity.project.Project;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface ProjectService {

  Optional<Project> projectByName(String name);

  Map<String, Object> upsert(ProjectRequestDto projectRequestDto)
      throws NotFoundException,IOException;

  BuildState build(Long ProjectId, GitlabWebHookDto webHookDto) throws ChangeSetPersister.NotFoundException, IOException;

  StateResponseDto checkState(StateRequestDto stateRequestDto) throws ChangeSetPersister.NotFoundException;

  List<FrameworkTypeResponseDto> getFrameworkType();

  FrameworkVersionResponseDto getFrameworkVersion(Long typeId) throws ChangeSetPersister.NotFoundException;

  List<ProjectListResponseDto> projectList() throws ChangeSetPersister.NotFoundException, UserDefindedException;

  List<BuildTotalResponseDto> buildTotal(Long projectId) throws NotFoundException;
}
