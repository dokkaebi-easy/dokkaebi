package com.ssafy.dockerby.core.docker.vo.docker;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class BuildConfig extends DockerbyConfig {

  private String projectDirectory;

  private String buildPath;

  private String type;

  public BuildConfig() {
  }

  public BuildConfig(String name, String framework, String version, List<DockerbyProperty> properties,
      String projectDirectory, String buildPath, String type) {
    super(name, framework, version, properties);
    this.projectDirectory = projectDirectory;
    this.buildPath = buildPath;
    this.type = type;
  }

  public String getProjectDirectory() {
    return projectDirectory;
  }

  public String getBuildPath() {
    return buildPath;
  }

  public String getType() {
    return type;
  }
  public List<DockerbyProperty> getProperties() {
    return super.getProperties();
  }
  public boolean useNginx() {
    return (getFramework().equals("Vue") || getFramework().equals("React") && getType().equals("Yes"));
  }
}
