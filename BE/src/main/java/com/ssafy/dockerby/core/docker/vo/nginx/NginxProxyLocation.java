package com.ssafy.dockerby.core.docker.vo.nginx;

public class NginxProxyLocation {

  private final String location;
  private final String url;

  public NginxProxyLocation(String location, String url) {
    this.location = location;
    this.url = url;
  }

  public String getLocation() {
    return location;
  }

  public String getUrl() {
    return url;
  }

  public boolean isEmpty() {
    return location.isBlank() && url.isBlank();
  }

}
