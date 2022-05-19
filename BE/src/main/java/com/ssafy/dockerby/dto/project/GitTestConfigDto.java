package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.entity.git.GitlabConfig;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GitTestConfigDto {
    private String hostUrl;
    private Long accessTokenId;   // git connection accessToken
    private Long projectId;
    private String repositoryUrl;
    private String branchName;    // branchSpecifier

    public static GitTestConfigDto from(GitlabConfig config) {
        return new GitTestConfigDto(
                config.getHostUrl(),
                config.getToken().getId(),
                config.getProject().getId(),
                config.getRepositoryUrl(),
                config.getBranchName()
        );

    }
}

