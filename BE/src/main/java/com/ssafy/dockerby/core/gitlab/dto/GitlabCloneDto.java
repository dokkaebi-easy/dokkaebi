package com.ssafy.dockerby.core.gitlab.dto;

import lombok.Getter;

@Getter
public class GitlabCloneDto {

  private String accessToken;
  private String gitHttpUrl;
  private String branch;

  public GitlabCloneDto(String accessToken, String gitHttpUrl, String branch) {
    this.accessToken = accessToken;
    this.gitHttpUrl = gitHttpUrl;
    this.branch = branch;
  }

  public static GitlabCloneDto of(String accessToken, String gitHttpUrl, String branch) {
    return new GitlabCloneDto(accessToken,gitHttpUrl,branch);
  }
}
