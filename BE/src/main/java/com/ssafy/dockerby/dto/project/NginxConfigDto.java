package com.ssafy.dockerby.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.dockerby.core.docker.vo.nginx.NginxConfig;
import com.ssafy.dockerby.core.docker.vo.nginx.NginxHttpsOption;
import com.ssafy.dockerby.core.docker.vo.nginx.NginxProxyLocation;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NginxConfigDto {
  private List<String> domains;
  private List<NginxProxyLocation> locations;

  private boolean https;
  private NginxHttpsOption httpsOption;

  @JsonIgnore
  private int maxBodySize = 50;

  public NginxConfigDto(List<String> domains, List<NginxProxyLocation> locations, boolean https,
      NginxHttpsOption httpsOption) {
    this.domains = domains;
    this.locations = locations;
    this.https = https;
    this.httpsOption = httpsOption;
  }

  public static NginxConfigDto from(NginxConfig nginx) {
    return new NginxConfigDto(nginx.getDomains(), nginx.getLocations(), nginx.isHttps(), nginx.getNginxHttpsOption());
  }

  public static NginxConfigDto from() {
    return new NginxConfigDto(new ArrayList<>(), new ArrayList<>(), false, new NginxHttpsOption("","",""));
  }

}
