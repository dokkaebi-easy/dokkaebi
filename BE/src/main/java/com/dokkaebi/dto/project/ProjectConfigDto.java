package com.dokkaebi.dto.project;

import java.util.List;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectConfigDto {

  private Long projectId;

  @NotBlank
  private String projectName;

  private List<BuildConfigDto> buildConfigs;

  private GitConfigDto gitConfig;

  private NginxConfigDto nginxConfig;

  private List<DBConfigDto> dbConfigs;

  public static ProjectConfigDto of(Long projectId, String projectName, List<BuildConfigDto> buildConfigs,
      GitConfigDto gitConfig, NginxConfigDto nginxConfigDto, List<DBConfigDto> dbConfigs) {
    return new ProjectConfigDto(projectId, projectName, buildConfigs, gitConfig, nginxConfigDto, dbConfigs);
  }

  @Override
  public String toString() {
    return "ProjectConfigDto{" +
      "projectId=" + projectId +
      ", projectName='" + projectName + '\'' +
      ", buildConfigs=" + buildConfigs +
      ", gitConfig=" + gitConfig +
      ", nginxConfig=" + nginxConfig +
      '}';
  }
}
