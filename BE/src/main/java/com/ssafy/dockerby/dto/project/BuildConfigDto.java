package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildConfigDto {

  @Positive
  private Long frameworkId;

  @NotBlank
  private String name;

  @NotBlank
  private String version;

  // Optional
  private String type;

  @NotNull
  private String projectDirectory;

  private String buildPath;

  private List<ConfigProperty> properties;

  @Override
  public String toString() {
    return "BuildConfigDto{" +
        "frameworkId=" + frameworkId +
        ", name='" + name + '\'' +
        ", version='" + version + '\'' +
        ", type='" + type + '\'' +
        ", projectDirectory='" + projectDirectory + '\'' +
        ", buildPath='" + buildPath + '\'' +
        ", properties=" + properties +
        '}';
  }
}
