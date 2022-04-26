package com.ssafy.dockerby.entity.git;

import com.ssafy.dockerby.dto.git.GitTokenRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

  @OneToOne(mappedBy = "token")
  GitlabConfig config;

  public static GitlabAccessToken of(String name, String accessToken) {
    return new GitlabAccessToken(null, name, accessToken, null);
  }

  public static GitlabAccessToken from(GitTokenRequestDto requestDto) {
    return new GitlabAccessToken(
        null,
        requestDto.getName(),
        requestDto.getAccessToken(),
        null);
  }

  public void setConfig(GitlabConfig config) {
    this.config = config;
  }

  public void softDelete() {
    name = null;
    accessToken = null;
  }

  public void update(GitTokenRequestDto requestDto) {
    this.name = requestDto.getName();
    this.accessToken = requestDto.getAccessToken();
  }
}
