package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildDetailResponseDto {

  @NotNull
  private String projectName;

  @NotNull
  private Long buildNumber;

  @Nullable
  private GitInfo gitInfo;

  @NotNull
  private String consoleLog;

  @Getter
  @Builder
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class GitInfo{

    private String username;

    private String gitRepositoryUrl;

    private String gitBranch;
  }

}
