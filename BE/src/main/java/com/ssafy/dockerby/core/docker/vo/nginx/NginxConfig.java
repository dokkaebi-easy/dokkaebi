package com.ssafy.dockerby.core.docker.vo.nginx;

import java.util.List;

public class NginxConfig {

  private List<String> domains;
  private List<NginxProxyLocation> locations;
  private boolean https;
  private NginxHttpsOption nginxHttpsOption;

  private int maxBodySize = 50;

  public NginxConfig() {
  }

  public NginxConfig(List<String> domains, List<NginxProxyLocation> locations, boolean https,
      NginxHttpsOption nginxHttpsOption, int maxBodySize) {
    this.domains = domains;
    this.locations = locations;
    this.https = https;
    this.nginxHttpsOption = nginxHttpsOption;
    this.maxBodySize = maxBodySize;
  }

  public List<String> getDomains() {
    return domains;
  }

  public List<NginxProxyLocation> getLocations() {
    return locations;
  }

  public boolean isHttps() {
    return https;
  }

  public NginxHttpsOption getNginxHttpsOption() {
    return nginxHttpsOption;
  }

  public int getMaxBodySize() {
    return maxBodySize;
  }

  public boolean isEmpty() {
    return domains.isEmpty();
  }


}
