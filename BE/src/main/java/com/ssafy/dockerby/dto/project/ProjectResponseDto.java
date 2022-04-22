package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectResponseDto {

  @NotNull
  private Long projectId;

  @NotNull
  private String projectName;

  private String configLocation;

  private String state;

  @Override
  public String toString() {
    return "ProjectResponseDto{" +
      "projectId=" + projectId +
      ", projectName='" + projectName + '\'' +
      ", configLocation='" + configLocation + '\'' +
      ", state='" + state + '\'' +
      '}';
  }
}
