package com.ssafy.dockerby.controller.git;

import com.ssafy.dockerby.core.gitlab.GitlabAccess;
import com.ssafy.dockerby.dto.git.GitTokenRequestDto;
import com.ssafy.dockerby.dto.git.GitTokenResponseDto;
import com.ssafy.dockerby.dto.project.GitTestConfigDto;
import com.ssafy.dockerby.entity.git.GitlabAccessToken;
import com.ssafy.dockerby.service.git.GitlabService;
import io.swagger.annotations.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
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
@RestController
@RequestMapping("/api/git")
@RequiredArgsConstructor
public class GitController {

  private final GitlabService gitlabService;

  @GetMapping("/tokens")
  public List<GitTokenResponseDto> tokens() {
    return gitlabService.tokens();
  }


  @PostMapping("/token")
  public List<GitTokenResponseDto> createToken(
      @Validated @RequestBody GitTokenRequestDto requestDto) {
    gitlabService.createToken(requestDto);
    return gitlabService.tokens();
  }

  @PatchMapping("/token")
  public List<GitTokenResponseDto> updateToken(
      @Validated @RequestBody GitTokenRequestDto requestDto) {
    gitlabService.updateToken(requestDto);
    return gitlabService.tokens();
  }

  @DeleteMapping("/token")
  public List<GitTokenResponseDto> deleteToken(@RequestBody Long id) {
    gitlabService.deleteToken(id);
    return gitlabService.tokens();
  }

  @PostMapping("/testConnection")
  public ResponseEntity testConnection(@RequestBody GitTestConfigDto requestDto) {
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