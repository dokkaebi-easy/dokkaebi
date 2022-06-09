package com.dokkaebi.controller.git;

import com.dokkaebi.core.gitlab.GitlabAccess;
import com.dokkaebi.dto.git.GitTokenRequestDto;
import com.dokkaebi.dto.git.GitTokenResponseDto;
import com.dokkaebi.dto.project.GitTestConfigDto;
import com.dokkaebi.entity.git.GitlabAccessToken;
import com.dokkaebi.service.git.GitlabService;
import io.swagger.annotations.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"Git credentials"})
@Slf4j
@RestController
@RequestMapping("/api/git")
@RequiredArgsConstructor
public class GitController {

  private final GitlabService gitlabService;

  @GetMapping("/tokens")
  public List<GitTokenResponseDto> tokens() {
    log.info("API Request received");
    return gitlabService.tokens();
  }


  @PostMapping("/token")
  public List<GitTokenResponseDto> createToken(
      @Validated @RequestBody GitTokenRequestDto requestDto) {
    log.info("API Request received");
    gitlabService.createToken(requestDto);
    return gitlabService.tokens();
  }

  @PatchMapping("/token")
  public List<GitTokenResponseDto> updateToken(
      @Validated @RequestBody GitTokenRequestDto requestDto) {
    log.info("API Request received");
    gitlabService.updateToken(requestDto);
    return gitlabService.tokens();
  }

  @DeleteMapping("/token")
  public List<GitTokenResponseDto> deleteToken(@RequestBody Long id) {
    log.info("API Request received");
    gitlabService.deleteToken(id);
    return gitlabService.tokens();
  }

  @PostMapping("/testConnection")
  public ResponseEntity testConnection(@RequestBody GitTestConfigDto requestDto) {
    log.info("API Request received : projectId = {}",requestDto.getProjectId());
    GitlabAccessToken token = gitlabService.token(requestDto.getAccessTokenId());
    String firstResponseMessage = GitlabAccess.isProjectToken(requestDto.getHostUrl(), token.getAccessToken());
    String secondResponseMessage = GitlabAccess.isGitlabRepositoryUrl(requestDto.getProjectId(), requestDto.getRepositoryUrl());
    String thirdResponseMessage = GitlabAccess.isGitlabBranch(requestDto.getProjectId(), requestDto.getBranchName());

    Map<String, Object> map = new HashMap<>();

    if (!firstResponseMessage.equals("Success")) {
      map.put("status", firstResponseMessage);
    } else if (!secondResponseMessage.equals("Success")) {
      map.put("status", secondResponseMessage);
    } else if (!thirdResponseMessage.equals("Success")) {
      map.put("status", thirdResponseMessage);
    } else {
      map.put("status", "Success");
    }
    return ResponseEntity.ok(map);
  }
}