package com.dokkaebi.core.docker.vo.docker;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class BuildConfig extends DokkaebiConfig {

  private String projectDirectory;

  private String buildPath;

  private String type;

  public BuildConfig() {
  }

  public BuildConfig(String name, String framework, String version, List<DokkaebiProperty> properties,
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
  public List<DokkaebiProperty> getProperties() {
    return super.getProperties();
  }
  public boolean useNginx() {
    return (getFramework().equals("Vue") || getFramework().equals("React") && getType().equals("Yes"));
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
