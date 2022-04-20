package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
