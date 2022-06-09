package com.dokkaebi.dto.project;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
