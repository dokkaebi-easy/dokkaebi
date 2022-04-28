package com.ssafy.dockerby.core.docker.dto;

import com.ssafy.dockerby.dto.project.NginxConfigDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DockerNginxConfig {

  @Builder.Default
  private List<String> domains = new ArrayList<>();

  @Builder.Default
  private List<ProxyLocation> locations = new ArrayList<>();
  @Builder.Default
  private boolean https = false;
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

    public boolean checkBlank() {
      return location.isBlank() && url.isBlank();
    }

    public static ProxyLocation of(String location, String url) {
      return new ProxyLocation(location,url);
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class HttpsOption {
    private String sslCertificate;
    private String sslCertificateKey;
    private String sslPath;

    public static HttpsOption of(String sslCertificate, String sslCertificateKey, String sslPath) {
      return new HttpsOption(sslCertificate,sslCertificateKey, sslPath);
    }
  }

  public static DockerNginxConfig from(NginxConfigDto dto) {
    return DockerNginxConfig.builder()
        .domains(dto.getDomains())
        .locations(dto.getLocations())
        .https(dto.isHttps())
        .httpsOption(dto.getHttpsOption())
        .build();
  }

}
