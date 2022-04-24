package com.ssafy.dockerby.repository.project;

import com.ssafy.dockerby.entity.project.enums.frameworks.Framework;
import com.ssafy.dockerby.entity.project.enums.frameworks.FrameworkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FrameworkRepository extends JpaRepository<Framework,Long> {
  Optional<List<Framework>> findAllByFrameworkTypeId(long frameworkTypeId);
}
