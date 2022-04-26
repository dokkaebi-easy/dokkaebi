package com.ssafy.dockerby.entity.git;

import com.ssafy.dockerby.dto.project.GitConfigDto;
import com.ssafy.dockerby.entity.project.Project;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GitlabConfig {

  @Id
  @GeneratedValue
  @Column(name = "gitlab_config_id")
  private Long id;

  private String name;
  private String hostUrl;
  private String secretToken;
  private String repositoryUrl;
  private String branchName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "gitlab_account_id")
  GitlabAccount account;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "gitlab_access_token_id")
  GitlabAccessToken token;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  Project project;

  public static GitlabConfig of(String name, String hostUrl, String secretToken, String repositoryUrl, String branchName) {
    return new GitlabConfig(null,name,hostUrl,secretToken,repositoryUrl,branchName,null,null, null);
  }

  public static GitlabConfig from(GitConfigDto configDto) {
    return new GitlabConfig(
        null,
        configDto.getName(),
        configDto.getHostUrl(),
        configDto.getSecretToken(),
        configDto.getRepositoryUrl(),
        configDto.getBranchName(),
        null, null, null
    );
  }

  public void setAccount(GitlabAccount account) {
    this.account = account;
    this.account.setConfig(this);
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
    this.name = configDto.getName();
    this.hostUrl = configDto.getHostUrl();
    this.secretToken = configDto.getSecretToken();
    this.repositoryUrl = configDto.getRepositoryUrl();
    this.branchName = configDto.getBranchName();


  }
}
