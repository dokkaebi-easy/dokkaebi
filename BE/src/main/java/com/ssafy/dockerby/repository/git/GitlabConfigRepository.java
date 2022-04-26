package com.ssafy.dockerby.repository.git;

import com.ssafy.dockerby.entity.git.GitlabConfig;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GitlabConfigRepository extends CrudRepository<GitlabConfig, Long> {
  Optional<GitlabConfig > findByProjectId(Long projectId);
}
