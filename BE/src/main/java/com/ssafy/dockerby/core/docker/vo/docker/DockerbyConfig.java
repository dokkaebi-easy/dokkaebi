package com.ssafy.dockerby.core.docker.vo.docker;

import java.util.ArrayList;
import java.util.List;

public class DockerbyConfig {

  private final String framework;
  private final String version;
  private final String name;
  private final List<DockerbyProperty> properties;

  DockerbyConfig(String name, String framework, String version, List<DockerbyProperty> properties) {
    this.framework = framework;
    this.version = version;
    this.name = name;
    this.properties = properties;
  }

  public List<String> propertyCommands() {
    List<String> commands = new ArrayList<>();
    properties.forEach(property -> commands.add(property.command()));
    return commands;
  }

  public boolean checkEmpty() {
    return framework.isBlank() && version.isBlank() && properties.isEmpty();
  }

  public String getFramework() {
    return framework;
  }

  public String getVersion() {
    return version;
  }

  public String getName() {
    return name;
  }

  public List<DockerbyProperty> getProperties() {
    return properties;
  }

  public void addProperty(DockerbyProperty property) {
    properties.add(property);
  }
}
