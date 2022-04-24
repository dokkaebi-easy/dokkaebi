package com.ssafy.dockerby.core.docker.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NginxConfig {

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
  public static class ProxyLocation {
    private final String location;
    private final String url;

    public ProxyLocation(String location, String url) {
      this.location = location;
      this.url = url;
    }
  }

  @Getter
  public static class HttpsOption {
    private String sslCertificate;
    private String sslCertificateKey;
    private String sslPath;

    public HttpsOption(String sslCertificate, String sslCertificateKey, String sslPath) {
      this.sslCertificate = sslCertificate;
      this.sslCertificateKey = sslCertificateKey;
      this.sslPath = sslPath;
    }
  }

}
