package com.ssafy.dockerby.repository.project;

import com.ssafy.dockerby.entity.project.states.Run;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunRepository extends JpaRepository<Run,Long> {
}
