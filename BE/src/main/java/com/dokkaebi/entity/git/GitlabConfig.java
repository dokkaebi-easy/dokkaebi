package com.dokkaebi.entity.git;

import com.dokkaebi.entity.project.Project;
import com.dokkaebi.dto.project.GitConfigDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GitlabConfig {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "gitlab_config_id")
  private Long id;
  private String hostUrl;
  private String secretToken;
  private String repositoryUrl;
  private String branchName;

  private Long gitProjectId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "gitlab_access_token_id")
  GitlabAccessToken token;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  Project project;

  public static GitlabConfig of(String hostUrl, String secretToken, String repositoryUrl, String branchName, Long gitProjectId) {
    return new GitlabConfig().builder()
        .hostUrl(hostUrl)
        .secretToken(secretToken)
        .repositoryUrl(repositoryUrl)
        .branchName(branchName)
        .gitProjectId(gitProjectId)
        .build();
  }

  public static GitlabConfig from(GitConfigDto configDto) {
    return new GitlabConfig().builder()
        .hostUrl(configDto.getHostUrl())
        .secretToken(configDto.getSecretToken())
        .repositoryUrl(configDto.getRepositoryUrl())
        .branchName(configDto.getBranchName())
        .gitProjectId(configDto.getGitProjectId())
        .build();
  }

  public void setToken(GitlabAccessToken token) {
    this.token = token;
    this.token.setConfig(this);
  }

  public void setProject(Project project) {
    this.project = project;
    this.project.setConfig(this);
  }

  public void update(GitConfigDto configDto) {
    this.hostUrl = configDto.getHostUrl();
    this.secretToken = configDto.getSecretToken();
    this.repositoryUrl = configDto.getRepositoryUrl();
    this.branchName = configDto.getBranchName();
  }
}
