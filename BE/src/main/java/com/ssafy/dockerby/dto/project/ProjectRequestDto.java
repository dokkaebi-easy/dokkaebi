package com.ssafy.dockerby.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
