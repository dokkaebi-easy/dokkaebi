package com.ssafy.dockerby.core.docker.vo.nginx;

public class NginxProxyLocation {

  private String location;
  private String url;

  public NginxProxyLocation() {
  }

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

  public boolean checkEmpty() {
    return location.isBlank() && url.isBlank();
  }

}
