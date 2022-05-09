package com.ssafy.dockerby.service.project;

import com.ssafy.dockerby.common.exception.UserDefindedException;
import com.ssafy.dockerby.core.gitlab.dto.GitlabWebHookDto;
import com.ssafy.dockerby.dto.project.*;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.entity.project.enums.StateType;
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

  boolean projectIsFailed(Long projectId) throws NotFoundException;

  void build(Long ProjectId, GitlabWebHookDto webHookDto) throws NotFoundException, IOException;

  void pullStart(Long projectId, GitlabWebHookDto webHookDto) throws NotFoundException, IOException;

  void buildStart(Long projectId, GitlabWebHookDto webHookDto) throws NotFoundException, IOException;

  void runStart(Long projectId, GitlabWebHookDto webHookDto) throws NotFoundException, IOException;

  StateType updateProjectDone(Long projectId) throws NotFoundException;


  List<BuildTotalResponseDto> buildTotal(Long projectId)
    throws NotFoundException;

  BuildDetailResponseDto buildDetail(Long buildStateId)
      throws NotFoundException;

  List<ProjectListResponseDto> projectList() throws NotFoundException, UserDefindedException;

  void deleteProject(Long projectId);
}
