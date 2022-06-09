package com.dokkaebi.repository.project;

import com.dokkaebi.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
  Optional<Project> findOneByProjectName(String projectName);
}