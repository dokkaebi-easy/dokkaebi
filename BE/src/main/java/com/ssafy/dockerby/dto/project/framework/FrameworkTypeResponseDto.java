package com.ssafy.dockerby.dto.project.framework;

import com.ssafy.dockerby.entity.core.FrameworkType;
import com.sun.istack.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FrameworkTypeResponseDto {

  @NotNull
  private Long id;

  @NotNull
  private String name;

  public static FrameworkTypeResponseDto from(FrameworkType frameworkType) {
    return new FrameworkTypeResponseDto(frameworkType.getId(), frameworkType.getFrameworkName());
  }

}
