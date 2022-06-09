package com.dokkaebi.core.docker.vo.docker;

import java.util.ArrayList;
import java.util.List;


public class DokkaebiConfig {

  private String framework;
  private String version;
  private String name;
  private List<DokkaebiProperty> properties;

  public DokkaebiConfig() {
  }

  DokkaebiConfig(String name, String framework, String version, List<DokkaebiProperty> properties) {
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

  public List<DokkaebiProperty> getProperties() {
    return properties;
  }

  public void addProperty(DokkaebiProperty property) {
    properties.add(property);
  }
}
