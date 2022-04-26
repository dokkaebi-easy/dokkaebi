package com.ssafy.dockerby.controller.git;

import com.ssafy.dockerby.dto.git.GitAccountRequestDto;
import com.ssafy.dockerby.dto.git.GitAccountResponseDto;
import com.ssafy.dockerby.dto.git.GitTokenRequestDto;
import com.ssafy.dockerby.dto.git.GitTokenResponseDto;
import com.ssafy.dockerby.service.git.GitlabService;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

  @GetMapping("/accounts")
  public List<GitAccountResponseDto> accounts() {
    return gitlabService.accounts();
  }

  @PostMapping("/token")
  public List<GitTokenResponseDto> createToken(
      @Validated @RequestBody GitTokenRequestDto requestDto) {
    gitlabService.createToken(requestDto);
    return gitlabService.tokens();
  }

  @PostMapping("/account")
  public List<GitAccountResponseDto> createAccount(
      @Validated @RequestBody GitAccountRequestDto requestDto) {
    gitlabService.createAccount(requestDto);
    return gitlabService.accounts();
  }

  @PatchMapping("/token")
  public List<GitTokenResponseDto> updateToken(
      @Validated @RequestBody GitTokenRequestDto requestDto) {
    gitlabService.updateToken(requestDto);
    return gitlabService.tokens();
  }

  @PatchMapping("/account")
  public List<GitAccountResponseDto> updateAccount(
      @Validated @RequestBody GitAccountRequestDto requestDto) {
    gitlabService.updateAccount(requestDto);
    return gitlabService.accounts();
  }

  @DeleteMapping("/token")
  public List<GitTokenResponseDto> deleteToken(@RequestBody Long id) {
    gitlabService.deleteToken(id);
    return gitlabService.tokens();
  }

  @DeleteMapping("/account")
  public List<GitAccountResponseDto> deleteAccount(@RequestBody Long id) {
    gitlabService.deleteAccount(id);
    return gitlabService.accounts();
  }
}
