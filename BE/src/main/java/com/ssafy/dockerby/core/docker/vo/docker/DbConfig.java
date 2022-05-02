package com.ssafy.dockerby.core.docker.vo.docker;

import java.util.List;

public class DbConfig extends DockerbyConfig {

  private final String dumpLocation;

  public DbConfig(String name, String framework, String version, List<DockerbyProperty> properties,
      String dumpLocation) {
    super(name, framework, version, properties);
    this.dumpLocation = dumpLocation;
  }

  public String getDumpLocation() {
    return dumpLocation;
  }
}
