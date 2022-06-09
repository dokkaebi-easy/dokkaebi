package com.dokkaebi.dto.project.framework;

import java.util.List;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FrameworkVersionResponseDto {

  private String name;

  private List<String> frameworkVersion;

  private List<String> buildTool;

  public static FrameworkVersionResponseDto from(String name, List<String> versions, List<String> buildTools) {
    return new FrameworkVersionResponseDto(name, versions, buildTools);
  }

}
