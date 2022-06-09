package com.dokkaebi.repository.git;

import com.dokkaebi.entity.git.GitlabConfig;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface GitlabConfigRepository extends CrudRepository<GitlabConfig, Long> {
  Optional<GitlabConfig > findByProjectId(Long projectId);
}
