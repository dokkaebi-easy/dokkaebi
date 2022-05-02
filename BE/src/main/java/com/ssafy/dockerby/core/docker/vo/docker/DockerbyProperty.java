package com.ssafy.dockerby.core.docker.vo.docker;

public class DockerbyProperty {
  private final String type;
  private final String host;
  private final String container;

  public DockerbyProperty(String type, String host, String container) {
    this.type = type;
    this.host = host;
    this.container = container;
  }

  public String command() {
    if(type.equals("publish")) {
      return new StringBuilder().append("-p ").append(host).append(":").append(container).toString();
    } else if (type.equals("volume")) {
      return new StringBuilder().append("-v ").append(host).append(":").append(container).toString();
    } else if (type.equals("environment")) {
      return new StringBuilder().append("-e ").append(host).append("=").append(container)
          .toString();
    } else {
      throw new IllegalArgumentException(type);
    }
  }

  public String getType() {
    return type;
  }

  public String getHost() {
    return host;
  }

  public String getContainer() {
    return container;
  }

}
