package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.project.*;
import com.ssafy.dockerby.dto.project.ProjectListResponseDto;
import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import com.ssafy.dockerby.dto.project.ProjectResponseDto;
import com.ssafy.dockerby.dto.project.StateRequestDto;
import com.ssafy.dockerby.dto.project.StateResponseDto;
import com.ssafy.dockerby.entity.project.ProjectState;
import java.security.Principal;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.IOException;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface ProjectService {
  ProjectResponseDto createProject(Principal principal,ProjectRequestDto projectRequestDto)
      throws IOException, UserDefindedException, NotFoundException;

  ProjectState build(ProjectRequestDto projectRequestDto) throws ChangeSetPersister.NotFoundException;

  StateResponseDto checkState(StateRequestDto stateRequestDto) throws ChangeSetPersister.NotFoundException;

  List<FrameworkTypeResponseDto> getFrameworkType();

  List<FrameworkVersionResponseDto> getFrameworkVersion(Long typeId) throws ChangeSetPersister.NotFoundException;

  List<ProjectListResponseDto> projectList() throws ChangeSetPersister.NotFoundException, UserDefindedException;

  List<BuildTotalResponseDto> buildTotal(Long projectId) throws NotFoundException;
}
