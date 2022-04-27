package com.ssafy.dockerby.dto.project;

import java.util.List;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FrameworkVersionResponseDto {

  private List<String> frameworkVersion;

  private List<String> buildTool;

  public static FrameworkVersionResponseDto from(List<String> versions, List<String> buildTools) {
    return new FrameworkVersionResponseDto(versions, buildTools);
  }

}
