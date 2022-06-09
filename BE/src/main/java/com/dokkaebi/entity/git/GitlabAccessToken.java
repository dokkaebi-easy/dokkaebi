package com.dokkaebi.entity.git;

import com.dokkaebi.dto.git.GitTokenRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GitlabAccessToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "gitlab_access_token_id")
  private Long id;

  private String name;
  private String accessToken;

  @OneToMany(mappedBy = "token")
  List<GitlabConfig> configs = new ArrayList<>();

  public static GitlabAccessToken of(String name, String accessToken) {
    return new GitlabAccessToken(null, name, accessToken, new ArrayList<>());
  }

  public static GitlabAccessToken from(GitTokenRequestDto requestDto) {
    return new GitlabAccessToken(
        null,
        requestDto.getName(),
        requestDto.getAccessToken(),
        new ArrayList<>());
  }

  public void setConfigs(List<GitlabConfig> configs) {
    this.configs = configs;
  }

  public void softDelete() {
    name = null;
    accessToken = null;
  }

  public void update(GitTokenRequestDto requestDto) {
    this.name = requestDto.getName();
    this.accessToken = requestDto.getAccessToken();
  }

  public void setConfig(GitlabConfig config) {
    this.configs.add(config);
  }
}
