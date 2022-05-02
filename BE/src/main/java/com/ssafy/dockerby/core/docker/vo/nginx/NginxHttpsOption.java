package com.ssafy.dockerby.core.docker.vo.nginx;

public class NginxHttpsOption {
  private final String sslCertificate;
  private final String sslCertificateKey;
  private final String sslPath;

  public NginxHttpsOption(String sslCertificate, String sslCertificateKey, String sslPath) {
    this.sslCertificate = sslCertificate;
    this.sslCertificateKey = sslCertificateKey;
    this.sslPath = sslPath;
  }

  public String getSslCertificate() {
    return sslCertificate;
  }

  public String getSslCertificateKey() {
    return sslCertificateKey;
  }

  public String getSslPath() {
    return sslPath;
  }

  public boolean isEmpty() {
    return sslCertificate.isBlank() || sslCertificateKey.isBlank() || sslPath.isBlank();
  }
}

