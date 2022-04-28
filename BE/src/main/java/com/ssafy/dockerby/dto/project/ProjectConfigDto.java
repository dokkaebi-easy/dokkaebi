package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import java.util.List;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectConfigDto {

  private Long projectId;

  @NotNull
  private String projectName;

  private List<BuildConfigDto> buildConfigs;

  private GitConfigDto gitConfig;

  private NginxConfigDto nginxConfig;

  public static ProjectConfigDto of(Long projectId, String projectName, List<BuildConfigDto> buildConfigs,
      GitConfigDto gitConfig, NginxConfigDto nginxConfigDto) {
    return new ProjectConfigDto(projectId, projectName, buildConfigs, gitConfig, nginxConfigDto);
  }

}
