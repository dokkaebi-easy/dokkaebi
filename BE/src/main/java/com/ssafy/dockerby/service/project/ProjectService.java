package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.dto.project.*;
import com.ssafy.dockerby.entity.project.ProjectState;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.IOException;
import java.util.List;

public interface ProjectService {
  ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) throws IOException, UserDefindedException;

  ProjectState build(ProjectRequestDto projectRequestDto) throws ChangeSetPersister.NotFoundException;

  StateResponseDto checkState(StateRequestDto stateRequestDto) throws ChangeSetPersister.NotFoundException;

  List<FrameworkTypeResponseDto> getFrameworkType();

  List<FrameworkVersionResponseDto> getFrameworkVersion(Long typeId) throws ChangeSetPersister.NotFoundException;
}
