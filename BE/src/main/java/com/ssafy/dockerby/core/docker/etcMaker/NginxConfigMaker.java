package com.ssafy.dockerby.core.docker.etcMaker;

import com.ssafy.dockerby.core.docker.vo.nginx.NginxConfig;
import com.ssafy.dockerby.core.docker.vo.nginx.NginxHttpsOption;
import com.ssafy.dockerby.core.docker.vo.nginx.NginxProxyLocation;
import java.util.List;

public class NginxConfigMaker {

  public String defaultConfig(NginxConfig config) {
    StringBuilder sb = new StringBuilder();
    sb.append(serverTagStart())
        .append(http())
        .append(serverName(config.getDomains()))
        .append(index())
        .append(defaultLocation());

    sb.append(clientMaxBodySize(config.getMaxBodySize()));
    sb.append(addLocations(config.getLocations()));

    sb.append(serverTagEnd());
    return sb.toString();
  }

  public String httpsConfig(NginxConfig config) {
    StringBuilder sb = new StringBuilder();
    sb.append(serverTagStart())
        .append(https(config.getNginxHttpsOption()))
        .append(serverName(config.getDomains()))
        .append(index())
        .append(defaultLocation());

    sb.append(clientMaxBodySize(config.getMaxBodySize()));
    sb.append(addLocations(config.getLocations()));

    sb.append(serverTagEnd());

    sb.append(serverTagStart())
        .append(http())
        .append(serverName(config.getDomains()))
        .append(httpMoved())
        .append(serverTagEnd());
    return sb.toString();
  }

  private String http() {
    StringBuilder sb = new StringBuilder();
    sb.append("    listen 80;\n")
        .append("    listen [::]:80;\n");
    return sb.toString();
  }

  private String https(NginxHttpsOption option) {
    StringBuilder sb = new StringBuilder();
    sb.append("    listen 443 ssl;\n")
        .append("    listen [::]:443 ssl;\n")
        .append('\n')
        .append("    ssl_certificate ").append(option.getSslCertificate()).append(";\n")
        .append("    ssl_certificate_key ").append(option.getSslCertificateKey()).append(";\n");
    return sb.toString();
  }

  private String clientMaxBodySize(int size) {
    return "    client_max_body_size " + size + "M;\n";
  }

  private String index() {
    return "    index index.html index.htm index.nginx-debian.html;\n";
  }

  private String addLocations(List<NginxProxyLocation> locations) {
    StringBuilder sb = new StringBuilder();
    for (NginxProxyLocation location : locations) {
      if (!location.checkEmpty()) {
        sb.append(addLocation(location));
      }
    }
    return sb.toString();
  }

  private String defaultLocation() {
    StringBuilder sb = new StringBuilder();
    sb.append("    location / {\n")
        .append("        error_page 405 =200 $uri;\n")
        .append("        root /usr/share/nginx/html;\n")
        .append("        try_files $uri $uri/ /index.html;\n")
        .append("    }\n");
    return sb.toString();
  }

  private String addLocation(NginxProxyLocation location) {
    StringBuilder sb = new StringBuilder();
    sb.append("    location ")
        .append(location.getLocation()).append(" {\n")
        .append("        proxy_pass ")
        .append(location.getUrl()).append(";\n")
        .append("        proxy_http_version 1.1;\n")
        .append("        proxy_set_header Connection \"\";\n")
        .append("\n")
        .append("        proxy_set_header Host $host;\n")
        .append("        proxy_set_header X-Real-IP $remote_addr;\n")
        .append("        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\n")
        .append("        proxy_set_header X-Forwarded-Proto $scheme;\n")
        .append("        proxy_set_header X-Forwarded-Host $host;\n")
        .append("        proxy_set_header X-Forwarded-Port $server_port;\n")
        .append("\n")
        .append("        proxy_read_timeout 300;\n")
        .append("    }\n");
    return sb.toString();
  }

  /**
   * https를 사용할 때 80번 포트의 요청은 443 요청으로 HTTP 301
   */
  private String httpMoved() {
    StringBuilder sb = new StringBuilder();
    sb.append("    return       301 https://$host$request_uri;\n}");
    return sb.toString();
  }

  private String serverTagStart() {
    return "server {\n";
  }

  private String serverTagEnd() {
    return "}\n";
  }

  private String serverName(List<String> domains) {
    StringBuilder sb = new StringBuilder();
    sb.append("    server_name ");
    domains.forEach(domain -> sb.append(domain).append(' '));
    sb.deleteCharAt(sb.length() - 1);
    sb.append(";\n");
    return sb.toString();
  }
}
