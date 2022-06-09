package com.dokkaebi.dto.project;

import com.dokkaebi.entity.git.GitlabConfig;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GitConfigDto {

  @Positive
  private Long gitProjectId;    // gitlab project id
  @NotBlank
  private String repositoryUrl;
  @NotBlank
  private String hostUrl;
  @Positive
  private Long accessTokenId;   // git connection credentials
  @NotBlank
  private String branchName;    // branchSpecifier
  @NotBlank
  private String secretToken;

  public boolean checkEmpty() {
    return hostUrl.isBlank() && accessTokenId ==0 && gitProjectId == 0 && repositoryUrl.isBlank() && secretToken.isBlank() && branchName.isBlank();
  }
  public static GitConfigDto from(GitlabConfig config) {
    return new GitConfigDto(
        config.getGitProjectId(),
        config.getRepositoryUrl(),
        config.getHostUrl(),
        config.getToken().getId(),
        config.getBranchName(),
        config.getSecretToken()
    );
  }
}
