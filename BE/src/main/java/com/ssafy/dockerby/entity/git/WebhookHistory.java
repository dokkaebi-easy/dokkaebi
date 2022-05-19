package com.ssafy.dockerby.entity.git;

import com.ssafy.dockerby.core.gitlab.dto.GitlabWebHookDto;
import com.ssafy.dockerby.entity.project.BuildState;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class WebhookHistory {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "webhook_history_id")
  private Long id;

  private String eventKind;
  private String username;
  private String gitHttpUrl;
  private String defaultBranch;
  private String repositoryName;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "build_state_id")
  private BuildState buildState;

  public static WebhookHistory of(GitlabWebHookDto dto) {
    return new WebhookHistory(
        null,
        dto.getEventKind(),
        dto.getUsername(),
        dto.getGitHttpUrl(),
        dto.getDefaultBranch(),
        dto.getRepositoryName(),
        null
    );
  }

  public void setBuildState(BuildState buildState) {
    this.buildState = buildState;
    buildState.setWebhookHistory(this);
  }
}
