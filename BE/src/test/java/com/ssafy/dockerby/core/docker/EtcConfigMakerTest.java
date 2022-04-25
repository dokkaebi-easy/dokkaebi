package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.dto.NginxConfig;
import com.ssafy.dockerby.core.docker.dto.NginxConfig.HttpsOption;
import com.ssafy.dockerby.core.docker.dto.NginxConfig.ProxyLocation;
import com.ssafy.dockerby.core.docker.etcMaker.NginxConfigMaker;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class EtcConfigMakerTest {

  private final NginxConfigMaker nginxConfigMaker = new NginxConfigMaker();
  
  @Test
  public void http_defaultConfig() {
    // given
    List<String> domains = new ArrayList<>();
    domains.add("senagi.site");
    domains.add("j6b105.p.ssafy.io");

    List<ProxyLocation> locations = new ArrayList<>();
    locations.add(new ProxyLocation("/api","http://localhost:8080"));
    locations.add(new ProxyLocation("/api/v1","http://senagi.site:8080"));

    NginxConfig config = NginxConfig.builder()
        .domains(domains)
        .locations(locations)
        .build();

    // when
    String result = nginxConfigMaker.defaultConfig(config);
//    try {
//      FileManager.saveFile(".","default.conf",result);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
    // then 
  }

  @Test
  public void https_defaultConfig() {
    // given
    List<String> domains = new ArrayList<>();
    domains.add("senagi.site");
    domains.add("j6b105.p.ssafy.io");

    List<ProxyLocation> locations = new ArrayList<>();
    locations.add(new ProxyLocation("/api","http://localhost:8080"));
    locations.add(new ProxyLocation("/api/v1","http://senagi.site:8080"));

    NginxConfig config = NginxConfig.builder()
        .domains(domains)
        .locations(locations)
        .isHttps(true)
        .httpsOption(new HttpsOption(
            "/etc/letsencrypt/live/senagi.site/fullchain.pem",
            "/etc/letsencrypt/live/senagi.site/privkey.pem",
            "etc/letsencrypt/live"))
        .build();

    // when
    String result = nginxConfigMaker.httpsConfig(config);
//    try {
//      FileManager.saveFile(".","default.conf",result);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
    // then
  }

}