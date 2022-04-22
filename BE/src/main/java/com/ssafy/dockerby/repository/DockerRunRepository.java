package com.ssafy.dockerby.repository;

import com.ssafy.dockerby.entity.project.states.DockerBuild;
import com.ssafy.dockerby.entity.project.states.DockerRun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DockerRunRepository extends JpaRepository<DockerRun,Long> {
}
