package com.ssafy.dockerby.repository;

import com.ssafy.dockerby.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProjectRepository extends JpaRepository<Project,Long> {
}
