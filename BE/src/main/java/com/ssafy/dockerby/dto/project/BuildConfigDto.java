package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import com.sun.istack.NotNull;
import java.io.ObjectInputFilter.Config;
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

  public static BuildConfigDto from(DockerContainerConfig config, String inputVersion, List<ConfigProperty> properties) {

    return BuildConfigDto.builder()
        .frameworkId(Long.valueOf(config.getFramework().ordinal()))
        .name(config.getName())
        .type(config.getType())
        .projectDirectory(config.getProjectDirectory())
        .buildPath(config.getBuildPath())
        .version(inputVersion)
        .properties(properties)
        .build();

  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class ConfigProperty {
    private String property;
    private String first;
    private String second;

    public static ConfigProperty of(String property, String first, String second) {
      return new ConfigProperty(property,first,second);
    }
  }
}
