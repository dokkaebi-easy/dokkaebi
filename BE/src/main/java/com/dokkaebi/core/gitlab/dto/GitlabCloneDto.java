package com.dokkaebi.core.gitlab.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GitlabCloneDto {

  private String accessToken;
  private String gitHttpUrl;
  private String branch;

  private Long gitProjectId;


  public static GitlabCloneDto of(String accessToken, String gitHttpUrl, String branch, Long gitProjectId) {
    return new GitlabCloneDto(accessToken,gitHttpUrl,branch,gitProjectId);
  }
}
