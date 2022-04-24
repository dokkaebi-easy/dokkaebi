package com.ssafy.dockerby.repository.project;

import com.ssafy.dockerby.entity.project.enums.states.Pull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PullRepository extends JpaRepository<Pull,Long> {
}
