package com.ssafy.dockerby.repository;

import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.entity.project.ProjectState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStateRepository extends JpaRepository<ProjectState,Long> {
}
