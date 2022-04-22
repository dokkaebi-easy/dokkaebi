package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.dto.ContainerConfig;
import java.util.List;
import java.util.Map;

public class DockerCommandMaker {

  private final String projectName;
  private final String rootDir;
  private String networkBridge;

  public DockerCommandMaker(String projectName, String rootDir) {
    this.projectName = projectName;
    this.rootDir = rootDir;
  }

  public String build(ContainerConfig config) {
    StringBuilder sb = new StringBuilder();
    // TODO : Image tag를 latest로 하는 것은 권장되지 않습니다.
    sb.append("docker build -t ")
        .append(config.getName()).append(":latest")
        .append(' ').append(rootDir).append(config.getProjectDirectory());
    return sb.toString();
  }

  public String run(ContainerConfig config) {
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

    return sb.toString();
  }

  public String bridge() {
    if(this.networkBridge == null)
      this.networkBridge = projectName + "_bridge";
    return "docker network create " + this.networkBridge;
  }
}
