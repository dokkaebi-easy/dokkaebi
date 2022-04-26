package com.ssafy.dockerby.service.git;

import com.ssafy.dockerby.dto.git.GitAccountRequestDto;
import com.ssafy.dockerby.dto.git.GitAccountResponseDto;
import com.ssafy.dockerby.dto.git.GitTokenRequestDto;
import com.ssafy.dockerby.dto.git.GitTokenResponseDto;
import com.ssafy.dockerby.dto.project.GitConfigDto;
import com.ssafy.dockerby.entity.project.Project;
import java.util.List;

public interface GitlabService {

  void createConfig(Project project, GitConfigDto configDto);

  void updateConfig(Project project, GitConfigDto configDto);

  GitConfigDto config(Long projectId);
  void deleteConfig(Long proejctId);

  void createToken(GitTokenRequestDto requestDto);
  void updateToken(GitTokenRequestDto requestDto);
  List<GitTokenResponseDto> tokens();
  void deleteToken(Long id);

  void createAccount(GitAccountRequestDto requestDto);
  void updateAccount(GitAccountRequestDto requestDto);
  List<GitAccountResponseDto> accounts();
  void deleteAccount(Long id);



}
