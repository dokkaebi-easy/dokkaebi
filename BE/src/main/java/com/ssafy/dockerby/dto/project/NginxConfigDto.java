package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.core.docker.dto.DockerNginxConfig;
import com.ssafy.dockerby.core.docker.dto.DockerNginxConfig.HttpsOption;
import com.ssafy.dockerby.core.docker.dto.DockerNginxConfig.ProxyLocation;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NginxConfigDto {
  private List<String> domains;
  private List<ProxyLocation> locations;

  private boolean https;
  private HttpsOption httpsOption;

  public static NginxConfigDto from(DockerNginxConfig nginx) {
    return new NginxConfigDto(nginx.getDomains(), nginx.getLocations(), nginx.isHttps(), nginx.getHttpsOption());
  }

  public static NginxConfigDto from() {
    List<String> domains = new ArrayList<>();
    domains.add("");
    List<ProxyLocation> locations = new ArrayList<>();
    locations.add(ProxyLocation.of("",""));
    return new NginxConfigDto(domains,locations,false,HttpsOption.of("","",""));
  }

  public boolean isNotUse() {
    return domains.get(0).isBlank() && locations.get(0).checkBlank();
  }
}
