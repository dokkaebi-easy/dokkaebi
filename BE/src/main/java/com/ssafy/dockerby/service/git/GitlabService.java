package com.ssafy.dockerby.service.git;

import com.ssafy.dockerby.dto.git.GitTokenRequestDto;
import com.ssafy.dockerby.dto.git.GitTokenResponseDto;
import com.ssafy.dockerby.dto.project.GitConfigDto;
import com.ssafy.dockerby.entity.git.GitlabAccessToken;
import com.ssafy.dockerby.entity.git.GitlabConfig;
import com.ssafy.dockerby.entity.project.Project;
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
