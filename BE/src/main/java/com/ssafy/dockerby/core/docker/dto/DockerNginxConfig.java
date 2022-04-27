package com.ssafy.dockerby.core.docker.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class DockerNginxConfig {

  @Builder.Default
  private List<String> domains = new ArrayList<>();

  @Builder.Default
  private List<ProxyLocation> locations = new ArrayList<>();
  @Builder.Default
  private boolean isHttps = false;
  private HttpsOption httpsOption;

  @Builder.Default
  private int maxBodySize = 50;

  public int getMaxBodySize() {
    return this.maxBodySize;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class ProxyLocation {
    private String location;
    private String url;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class HttpsOption {
    private String sslCertificate;
    private String sslCertificateKey;
    private String sslPath;
  }

}
