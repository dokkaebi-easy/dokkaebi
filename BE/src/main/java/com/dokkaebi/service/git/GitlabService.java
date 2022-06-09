package com.dokkaebi.service.git;

import com.dokkaebi.dto.git.GitTokenRequestDto;
import com.dokkaebi.dto.git.GitTokenResponseDto;
import com.dokkaebi.dto.project.GitConfigDto;
import com.dokkaebi.entity.git.GitlabAccessToken;
import com.dokkaebi.entity.git.GitlabConfig;
import com.dokkaebi.entity.project.Project;
import java.util.List;
import java.util.Optional;

public interface GitlabService {

  GitlabConfig createConfig(Project project, GitConfigDto configDto);

  GitlabConfig updateConfig(Project project, GitConfigDto configDto);

  Optional<GitlabConfig> config(Long projectId);
  void deleteConfig(Long proejctId);

  void createToken(GitTokenRequestDto requestDto);
  void updateToken(GitTokenRequestDto requestDto);
  List<GitTokenResponseDto> tokens();

  GitlabAccessToken token(Long id);
  void deleteToken(Long id);

}
