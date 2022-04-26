package com.ssafy.dockerby.dto.project;

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
  private Long projectId;
  private String repositoryUrl;
  private String secretToken;
  private String branchName;    // branchSpecifier
}
