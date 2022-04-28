package com.ssafy.dockerby.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.dockerby.entity.project.enums.StateType;
import java.time.LocalDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildDetailResponseDto {

  @NotNull
  private String projectName;

  @NotNull
  private Long projectId;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  private StateType stateType;

  @NotNull
  private Long buildNumber;

  @NotNull
  @CreatedDate
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime registDate;

  @NotNull
  private GitInfo gitInfo;

  @NotNull
  private String consoleLog;

  @Getter
  @Builder
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class GitInfo {

    @Builder.Default
    private String username = "";

    @Builder.Default
    private String gitRepositoryUrl = "";

    @Builder.Default
    private String gitBranch = "";

  }

  public static class BuildDetailResponseDtoBuilder {

    public BuildDetailResponseDtoBuilder gitInfo(GitInfo gitInfo) {
      if (gitInfo == null) {
        this.gitInfo = GitInfo.builder().build();
      }
      this.gitInfo = gitInfo;
      return this;
    }
  }

}
