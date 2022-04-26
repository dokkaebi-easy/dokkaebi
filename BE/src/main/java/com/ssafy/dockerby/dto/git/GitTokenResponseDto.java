package com.ssafy.dockerby.dto.git;

import com.ssafy.dockerby.entity.git.GitlabAccessToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GitTokenResponseDto {

  private Long id;
  private String name;

  public static GitTokenResponseDto of(Long id, String name) {
    return new GitTokenResponseDto(id,name);
  }

  public static GitTokenResponseDto from(GitlabAccessToken token) {
    return new GitTokenResponseDto(token.getId(), token.getName());
  }

}
