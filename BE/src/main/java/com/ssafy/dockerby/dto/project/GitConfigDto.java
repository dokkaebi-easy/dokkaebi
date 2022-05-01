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

  public boolean checkEmpty() {
    return hostUrl.isBlank() && accessTokenId ==0 && accountId == 0 && gitProjectId == 0 && repositoryUrl.isBlank() && secretToken.isBlank() && branchName.isBlank();
  }
  public static GitConfigDto from(GitlabConfig config) {
    if(config == null)
      return from();
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

  public static GitConfigDto from() {
    return new GitConfigDto("","",0L,0L,0L,"","","");
  }
}
