package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.entity.git.GitlabConfig;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GitConfigDto {
  private String name;
  private String hostUrl;
  private Long accessTokenId;   // git connection credentials
  private Long accountId;       // repositories credentials
  private Long gitProjectId;    // gitlab project id
  private String repositoryUrl;
  private String secretToken;
  private String branchName;    // branchSpecifier

  public static GitConfigDto from(GitlabConfig config) {
    return new GitConfigDto(
        config.getName(),
        config.getHostUrl(),
        config.getToken().getId(),
        config.getAccount().getId(),
        config.getGitProjectId(),
        config.getRepositoryUrl(),
        config.getSecretToken(),
        config.getBranchName()
    );

  }
}
