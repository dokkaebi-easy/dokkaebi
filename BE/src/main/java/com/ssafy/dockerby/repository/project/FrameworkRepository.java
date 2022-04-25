package com.ssafy.dockerby.repository.project;

import com.ssafy.dockerby.entity.project.frameworks.Version;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FrameworkRepository extends JpaRepository<Version,Long> {

}
