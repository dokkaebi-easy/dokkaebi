package com.ssafy.dockerby.repository.project;

import com.ssafy.dockerby.entity.project.states.DockerBuild;
import com.ssafy.dockerby.entity.project.states.GitPull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitPullRepository extends JpaRepository<GitPull,Long> {
}
