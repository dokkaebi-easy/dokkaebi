package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.entity.project.frameworks.Version;
import com.sun.istack.NotNull;
import java.util.List;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FrameworkVersionResponseDto {

  private List<String> frameworkVersion;

  private List<String> buildTool;

  public static FrameworkVersionResponseDto from(List<String> versions, List<String> buildTools) {
    return new FrameworkVersionResponseDto(versions, buildTools);
  }

}
