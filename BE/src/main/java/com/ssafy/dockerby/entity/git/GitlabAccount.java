package com.ssafy.dockerby.entity.git;

import com.ssafy.dockerby.dto.git.GitAccountRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GitlabAccount {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "gitlab_account_id")
  private Long id;

  private String email;
  private String password;
  private String username;

  @OneToMany(mappedBy = "account")
  List<GitlabConfig> configs = new ArrayList<>();

  public static GitlabAccount of(String email, String password, String username) {
    return new GitlabAccount(null,email,password,username,new ArrayList<>());
  }

  public static GitlabAccount from(GitAccountRequestDto requestDto) {
    return new GitlabAccount(
        null,
        requestDto.getEmail(),
        requestDto.getPassword(),
        requestDto.getEmail(),
        null
    );
  }

  public void setConfig(GitlabConfig config) {
    configs.add(config);
  }

  public void setConfigs(List<GitlabConfig> configs) {
    this.configs = configs;
  }

  public void softDelete() {
    email = null;
    password = null;
    username = null;
  }

  public void update(GitAccountRequestDto requestDto) {
    this.email = requestDto.getEmail();
    this.username = requestDto.getUsername();
    this.password = requestDto.getPassword();
  }
}
