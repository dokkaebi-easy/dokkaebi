package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRequestDto {

  @NotNull
  private String projectName;

  private String description;

  private String settingJson;

  @Override
  public String toString() {
    return "ProjectRequestDto{" +
      "projectName='" + projectName + '\'' +
      ", description='" + description + '\'' +
      ", settingJson='" + settingJson + '\'' +
      '}';
  }
}
