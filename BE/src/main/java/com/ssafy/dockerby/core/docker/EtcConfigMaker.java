package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.dto.DockerNginxConfig;
import com.ssafy.dockerby.core.docker.etcMaker.NginxConfigMaker;
import com.ssafy.dockerby.util.FileManager;
import java.io.IOException;

/**
 * Docker container 구동에 필요한 기타 Config 파일을 생성하는 클래스
 * [지원목록]
 *  NGINX - default.conf
 */
public class EtcConfigMaker {

  private static final NginxConfigMaker nginxConfigMaker = new NginxConfigMaker();

  /**
   * NGINX 환경 설정 파일을 가지고 default.conf 를 생성해준다.
   * HTTPS 옵션을 활성화 했을 때와 하지 않았을 때를 분리한다.
   * 기본으로 HTTP : 80 포트, HTTPS : 443 포트를 이용한다.
   * @param filePath default.conf를 저장할 위치 경로 (root 추천)
   * @param dockerNginxConfig nginx 환경 설정 dto
   * @throws IOException {@link FileManager} 에서 던지는 예외
   */
  public static void nginxConfig(String filePath, DockerNginxConfig dockerNginxConfig) throws IOException {
    String config = "";
    if(dockerNginxConfig.isHttps())
      config = nginxConfigMaker.httpsConfig(dockerNginxConfig);
    else
      config = nginxConfigMaker.defaultConfig(dockerNginxConfig);

    FileManager.saveFile(filePath,"default.conf",config);

  }
}
