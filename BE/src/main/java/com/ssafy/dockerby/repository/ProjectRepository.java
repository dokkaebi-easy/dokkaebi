package com.ssafy.dockerby.repository;

import com.ssafy.dockerby.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
  Optional<Project> findOneByProjectName(String projectName);
}
