package com.ssafy.dockerby.repository.project;

import com.ssafy.dockerby.entity.core.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrameworkRepository extends JpaRepository<Version,Long> {

}
