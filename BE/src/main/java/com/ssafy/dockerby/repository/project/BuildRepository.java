package com.ssafy.dockerby.repository.project;

import com.ssafy.dockerby.entity.project.enums.states.Build;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildRepository extends JpaRepository<Build,Long> {
}
