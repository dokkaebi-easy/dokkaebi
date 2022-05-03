package com.ssafy.dockerby.core.docker.vo.docker;

import com.ssafy.dockerby.util.PathParser;
import java.util.ArrayList;
import java.util.List;

public class DbConfig extends DockerbyConfig {

  private String dumpLocation;

  public DbConfig() {
  }

  public DbConfig(String name, String framework, String version, List<DockerbyProperty> properties,
      String dumpLocation) {
    super(name, framework, version, properties);
    this.dumpLocation = dumpLocation;
  }

  public String getDumpLocation() {
    return dumpLocation;
  }

}
