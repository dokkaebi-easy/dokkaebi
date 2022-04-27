package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.entity.core.FrameworkType;
import com.sun.istack.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FrameworkTypeResponseDto {

  @NotNull
  private Long frameworkTypeId;

  @NotNull
  private String frameworkName;

  public static FrameworkTypeResponseDto from(FrameworkType frameworkType) {
    return new FrameworkTypeResponseDto(frameworkType.getId(), frameworkType.getFrameworkName());
  }

}
