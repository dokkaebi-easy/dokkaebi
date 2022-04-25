package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.core.docker.dto.DockerNginxConfig.HttpsOption;
import com.ssafy.dockerby.core.docker.dto.DockerNginxConfig.ProxyLocation;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NginxConfigDto {
  private String domainUrl;
  private List<ProxyLocation> locations;

  private boolean https;
  private HttpsOption httpsOption;
}
