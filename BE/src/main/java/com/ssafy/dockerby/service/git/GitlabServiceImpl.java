package com.ssafy.dockerby.service.git;

import com.ssafy.dockerby.dto.git.GitAccountRequestDto;
import com.ssafy.dockerby.dto.git.GitAccountResponseDto;
import com.ssafy.dockerby.dto.git.GitTokenRequestDto;
import com.ssafy.dockerby.dto.git.GitTokenResponseDto;
import com.ssafy.dockerby.dto.project.GitConfigDto;
import com.ssafy.dockerby.entity.git.GitlabAccessToken;
import com.ssafy.dockerby.entity.git.GitlabAccount;
import com.ssafy.dockerby.entity.git.GitlabConfig;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.repository.git.GitlabAccessTokenRepository;
import com.ssafy.dockerby.repository.git.GitlabAccountRepository;
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
  private final GitlabAccountRepository accountRepository;
  private final GitlabConfigRepository configRepository;
  @Override
  public GitlabConfig createConfig(Project project, GitConfigDto configDto) {
    log.info("createConfig Start : projectName = {} ",project.getProjectName());
    GitlabConfig config = GitlabConfig.from(configDto);

    GitlabAccount account = accountRepository.findById(configDto.getAccountId())
        .orElseThrow(() -> new NotFoundException());

    GitlabAccessToken token = tokenRepository.findById(configDto.getAccessTokenId())
        .orElseThrow(()-> new NotFoundException());

    config.setProject(project);
    config.setAccount(account);
    config.setToken(token);

    configRepository.save(config);
    log.info("createConfig Done");
    return config;
  }

  @Override
  public GitlabConfig updateConfig(Project project, GitConfigDto configDto) {
    log.info("updateConfig Start : projectName = {} ",project.getProjectName());
    GitlabConfig config = configRepository.findByProjectId(project.getId())
        .orElseThrow(() -> new NotFoundException());

    config.update(configDto);

    if(config.getAccount().getId() != configDto.getAccountId()) {
      GitlabAccount newAccount = accountRepository.findById(configDto.getAccountId())
          .orElseThrow(() -> new NotFoundException());
      config.setAccount(newAccount);
    }

    if(config.getToken().getId() != configDto.getAccessTokenId()) {
      GitlabAccessToken newToken = tokenRepository.findById(configDto.getAccessTokenId())
          .orElseThrow(() -> new NotFoundException());
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
        .orElseThrow(() -> new NotFoundException());
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
        .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public void deleteToken(Long id) {
    log.info("deleteToken Start");
    GitlabAccessToken token = tokenRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());

    token.softDelete();
    log.info("deleteToken Done");
  }

  @Override
  public void createAccount(GitAccountRequestDto requestDto) {
    log.info("createAccount Start");
    GitlabAccount account = GitlabAccount.from(requestDto);
    accountRepository.save(account);
    log.info("createAccount Done");
  }

  @Override
  public void updateAccount(GitAccountRequestDto requestDto) {
    log.info("updateAccount Start");
    GitlabAccount account = accountRepository.findById(requestDto.getId())
        .orElseThrow(() -> new NotFoundException());
    account.update(requestDto);
    log.info("updateAccount Done");
  }

  @Override
  public List<GitAccountResponseDto> accounts() {
    log.info("accounts Start");
    List<GitAccountResponseDto> results = new ArrayList<>();
    accountRepository.findAllByEmailIsNotNull()
        .forEach(value -> results.add(GitAccountResponseDto.from(value)));
    log.info("accounts Done");
    return results;
  }

  @Override
  public GitlabAccount account(Long id) {
    log.info("account Start: accountId = {}",id);
    return accountRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public void deleteAccount(Long id) {
    log.info("deleteAccount Start : accountId = {}",id);
    GitlabAccount account = accountRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
    account.softDelete();
    log.info("deleteAccount Done");
  }
}


