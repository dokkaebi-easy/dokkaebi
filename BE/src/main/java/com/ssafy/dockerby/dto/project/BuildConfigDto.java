package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildConfigDto {

  @NotNull
  private String frameworkName;

  @NotNull
  private String name;

  @NotNull
  private String version;

  // Optional
  private String type;

  @NotNull
  private String projectDirectory;

  private String buildPath;

  private List<ConfigProperty> publish;
  private List<ConfigProperty> volume;
  private List<ConfigProperty> env;

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class ConfigProperty {
    private String first;
    private String second;
  }
}
