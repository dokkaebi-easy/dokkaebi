package com.ssafy.dockerby.service.git;

import com.ssafy.dockerby.dto.git.GitTokenRequestDto;
import com.ssafy.dockerby.dto.git.GitTokenResponseDto;
import com.ssafy.dockerby.dto.project.GitConfigDto;
import com.ssafy.dockerby.entity.git.GitlabAccessToken;
import com.ssafy.dockerby.entity.git.GitlabConfig;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.repository.git.GitlabAccessTokenRepository;
import com.ssafy.dockerby.repository.git.GitlabConfigRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GitlabServiceImpl implements GitlabService {

  private final GitlabAccessTokenRepository tokenRepository;
  private final GitlabConfigRepository configRepository;
  @Override
  public GitlabConfig createConfig(Project project, GitConfigDto configDto) {
    log.info("createConfig Start : projectName = {} ",project.getProjectName());
    GitlabConfig config = GitlabConfig.from(configDto);

    GitlabAccessToken token = tokenRepository.findById(configDto.getAccessTokenId())
        .orElseThrow(()-> new NotFoundException("GitlabAccessToken not found / id: "+configDto.getAccessTokenId()));

    config.setProject(project);
    config.setToken(token);

    configRepository.save(config);
    log.info("createConfig Done");
    return config;
  }

  @Override
  public GitlabConfig updateConfig(Project project, GitConfigDto configDto) {
    log.info("updateConfig Start : projectName = {} ",project.getProjectName());
    GitlabConfig config = configRepository.findByProjectId(project.getId())
        .orElseThrow(() -> new NotFoundException("GitlabConfig not found"));

    config.update(configDto);

    if(config.getToken().getId() != configDto.getAccessTokenId()) {
      GitlabAccessToken newToken = tokenRepository.findById(configDto.getAccessTokenId())
          .orElseThrow(() -> new NotFoundException("GitlabAccessToken not found / id: "+configDto.getAccessTokenId()));
      config.setToken(newToken);
    }
    log.info("updateConfig Done");
    return config;
  }

  @Override
  public Optional<GitlabConfig> config(Long projectId) {
    log.info("config Start : projectId = {} ",projectId);
    return configRepository.findByProjectId(projectId);
  }

  @Override
  public void deleteConfig(Long projectId) {
    log.info("deleteConfig Start : projectId = {} ",projectId);
    configRepository.deleteById(projectId);
    log.info("deleteConfig Done");
  }

  @Override
  public void createToken(GitTokenRequestDto requestDto) {
    log.info("createToken Start : name = {} ",requestDto.getName());
    GitlabAccessToken token = GitlabAccessToken.from(requestDto);
    tokenRepository.save(token);
    log.info("createToken Done");
  }

  @Override
  public void updateToken(GitTokenRequestDto requestDto) {
    log.info("updateToken Start : name = {} ",requestDto.getName());
    GitlabAccessToken token = tokenRepository.findById(requestDto.getId())
        .orElseThrow(() -> new NotFoundException("GitlabAccessToken not found / id: "+requestDto.getId()));
    token.update(requestDto);
    log.info("updateToken Done");
  }

  @Override
  public List<GitTokenResponseDto> tokens() {
    log.info("tokens Start ");
    List<GitTokenResponseDto> results = new ArrayList<>();
    tokenRepository.findAllByAccessTokenIsNotNull()
        .forEach(value -> results.add(GitTokenResponseDto.from(value)));
    log.info("tokens Done");
    return results;
  }

  @Override
  public GitlabAccessToken token(Long id) {
    log.info("token Start");
    return tokenRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("GitlabAccessToken not found / id: "+id));
  }

  @Override
  public void deleteToken(Long id) {
    log.info("deleteToken Start");
    GitlabAccessToken token = tokenRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("GitlabAccessToken not found / id: "+id));

    token.softDelete();
    log.info("deleteToken Done");
  }
}


