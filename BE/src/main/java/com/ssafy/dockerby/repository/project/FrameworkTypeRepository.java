package com.ssafy.dockerby.repository.project;

import com.ssafy.dockerby.entity.core.FrameworkType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrameworkTypeRepository extends JpaRepository<FrameworkType,Long> {
  Optional<FrameworkType> findByFrameworkName(String frameworkName);
}
