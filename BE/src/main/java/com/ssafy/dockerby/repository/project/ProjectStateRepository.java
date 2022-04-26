package com.ssafy.dockerby.repository.project;

import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.entity.project.ProjectState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectStateRepository extends JpaRepository<ProjectState,Long> {
  Optional<ProjectState> findByProjectId(Long projectId);

  List<ProjectState> findAllByProjectId(Long projectId);
}
