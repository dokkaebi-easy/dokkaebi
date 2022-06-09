package com.dokkaebi.core.docker.vo.docker;

import java.util.List;

public class DbConfig extends DokkaebiConfig {

  private String dumpLocation;

  public DbConfig() {
  }

  public DbConfig(String name, String framework, String version, List<DokkaebiProperty> properties,
      String dumpLocation) {
    super(name, framework, version, properties);
    this.dumpLocation = dumpLocation;
  }

  public String getDumpLocation() {
    return dumpLocation;
  }

  public String returnPort() {
    for (DokkaebiProperty property : this.getProperties()) {
      if ("publish".equals(property.getType())) {
        return property.getHost();
      }
    }
    return "";
  }

}
