package com.dokkaebi.service.project;

import com.dokkaebi.common.exception.UserDefindedException;
import com.dokkaebi.core.gitlab.dto.GitlabWebHookDto;
import com.dokkaebi.dto.project.BuildDetailResponseDto;
import com.dokkaebi.dto.project.BuildTotalResponseDto;
import com.dokkaebi.dto.project.ProjectConfigDto;
import com.dokkaebi.dto.project.ProjectListResponseDto;
import com.dokkaebi.entity.project.Project;
import com.dokkaebi.entity.project.enums.StateType;
import javassist.NotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
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

  StateType updateProjectDone(Long projectId, String duration) throws NotFoundException;

  List<BuildTotalResponseDto> buildTotal(Long projectId)
    throws NotFoundException;

  BuildDetailResponseDto buildDetail(Long buildStateId)
      throws NotFoundException;

  List<ProjectListResponseDto> projectList()
      throws NotFoundException, UserDefindedException, IOException;

  String makeDuration(LocalDateTime start, LocalDateTime end);

  void deleteProject(Long projectId) throws NotFoundException, IOException;

  void deleteContainer(Long projectId) throws NotFoundException, IOException;

  void stopContainer(Long projectId) throws IOException, NotFoundException;
}
