package com.ssafy.dockerby.core.gitlab.dto;

import com.ssafy.dockerby.entity.git.WebhookHistory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GitlabWebHookDto {

  private String eventKind;
  private String username;
  private String gitHttpUrl;
  private String defaultBranch;

  public static GitlabWebHookDto of(String eventKind, String username, String gitHttpUrl,
      String defaultBranch) {
    return new GitlabWebHookDto(eventKind, username, gitHttpUrl, defaultBranch
    );
  }

  public static GitlabWebHookDto from(WebhookHistory history) {
    return new GitlabWebHookDto(
        history.getEventKind(),
        history.getUsername(),
        history.getGitHttpUrl(),
        history.getDefaultBranch()
    );
  }

}
