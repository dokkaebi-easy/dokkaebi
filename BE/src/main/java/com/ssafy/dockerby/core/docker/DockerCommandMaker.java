package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class DockerCommandMaker {

  private final String projectName;

  private final String projectPath;

  private String networkBridge;

  public DockerCommandMaker(String projectPath, String projectName) {
    this.projectName = projectName;
    this.projectPath = projectPath;
  }

  public String build(DockerContainerConfig config) {
    log.info("build Start");
    StringBuilder sb = new StringBuilder();
    // TODO : Image tag를 latest로 하는 것은 권장되지 않습니다.
    sb.append("docker build -t ")
        .append(config.getName()).append(":latest")
        .append(' ').append(projectPath).append(config.getProjectDirectory());
    log.info("build Done");
    return sb.toString();
  }

  public String run(DockerContainerConfig config) {
    log.info("run Start");
    StringBuilder sb = new StringBuilder();
    sb.append("docker run -d --name ")
        .append(projectName).append('-').append(config.getName());

    for(Map.Entry<String, List<String>> iter : config.getProperties().entrySet()) {
      iter.getValue().forEach(val -> sb.append(" --").append(iter.getKey()).append(' ').append(val));
    }
    if(this.networkBridge != null)
      sb.append(" --network ").append(this.networkBridge);
    // TODO : Image tag를 latest로 하는 것은 권장되지 않습니다.
    sb.append(' ').append(config.getName()).append(":latest");

    log.info("run Done");
    return sb.toString();
  }

  public String bridge() {
    log.info("bridge Start");
    if(this.networkBridge == null)
      this.networkBridge = projectName + "_bridge";
    log.info("bridge Done");
    return "docker network create " + this.networkBridge;
  }

  public String removeContainer(DockerContainerConfig config) {
    log.info("removeContainer Start");
    StringBuilder sb = new StringBuilder();
    sb.append("docker rm -f ").append(projectName).append('-').append(config.getName());
    log.info("removeContainer Done");
    return sb.toString();
  }
}
