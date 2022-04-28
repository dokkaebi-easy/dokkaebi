package com.ssafy.dockerby.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.dockerby.entity.project.enums.StateType;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

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
