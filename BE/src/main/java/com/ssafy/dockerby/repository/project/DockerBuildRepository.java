package com.ssafy.dockerby.repository.project;

import com.ssafy.dockerby.entity.project.states.DockerBuild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DockerBuildRepository extends JpaRepository<DockerBuild,Long> {
}
