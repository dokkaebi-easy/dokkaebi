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
public class ProjectRequestDto {

  @NotNull
  private Long id;

  @NotNull
  private String projectName;

  private String description;

  private String settingJson;
}
