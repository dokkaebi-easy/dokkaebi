package com.ssafy.dockerby.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectRequestDto {

  @NotNull
  private String projectName;

  private List<BuildConfigDto> buildConfigs;

  private GitConfigDto gitConfig;

  private NginxConfigDto nginxConfig;

}
