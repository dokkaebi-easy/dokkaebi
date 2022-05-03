package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

  public static ProjectConfigDto of(Long projectId, String projectName, List<BuildConfigDto> buildConfigs,
      GitConfigDto gitConfig, NginxConfigDto nginxConfigDto) {
    return new ProjectConfigDto(projectId, projectName, buildConfigs, gitConfig, nginxConfigDto);
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
