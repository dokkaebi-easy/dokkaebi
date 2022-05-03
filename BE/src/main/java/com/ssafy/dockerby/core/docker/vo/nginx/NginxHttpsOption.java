package com.ssafy.dockerby.core.docker.vo.nginx;

public class NginxHttpsOption {
  private String sslCertificate;
  private String sslCertificateKey;
  private String sslPath;

  public NginxHttpsOption() {
  }

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

  public boolean checkEmpty() {
    return sslCertificate.isBlank() || sslCertificateKey.isBlank() || sslPath.isBlank();
  }
}

