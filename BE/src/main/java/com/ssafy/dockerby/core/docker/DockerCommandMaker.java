package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.vo.docker.BuildConfig;
import com.ssafy.dockerby.core.docker.vo.docker.DockerbyConfig;

public class DockerCommandMaker {

  private final String projectName;

  private final String projectPath;

  private String networkBridge;

  public DockerCommandMaker(String projectPath, String projectName) {
    this.projectName = projectName;
    this.projectPath = projectPath;
  }

  public String build(BuildConfig config) {
    StringBuilder sb = new StringBuilder();
    // TODO : Image tag를 latest로 하는 것은 권장되지 않습니다.
    sb.append("docker build -t ")
        .append(config.getName()).append(":latest")
        .append(' ').append(projectPath).append(config.getProjectDirectory());
    return sb.toString();
  }

  public String run(DockerbyConfig config) {
    StringBuilder sb = new StringBuilder();
    sb.append("docker run -d --name ")
        .append(projectName).append('-').append(config.getName()).append(' ');

    for(String command : config.propertyCommands()) {
      sb.append(command).append(' ');
    }

    if(this.networkBridge != null)
      sb.append(" --network ").append(this.networkBridge);

    // TODO : Image tag를 latest로 하는 것은 권장되지 않습니다.
    sb.append(' ').append(config.getName()).append(":latest");

    return sb.toString();
  }

  public String removeBridge() {
    if(this.networkBridge == null)
      this.networkBridge = projectName + "_bridge";
    return "docker network rm " + this.networkBridge;
  }

  public String addBridge() {
    if(this.networkBridge == null)
      this.networkBridge = projectName + "_bridge";
    return "docker network create " + this.networkBridge;
  }

  public String removeContainer(DockerbyConfig config) {
    StringBuilder sb = new StringBuilder();
    sb.append("docker rm -f ").append(projectName).append('-').append(config.getName());
    return sb.toString();
  }
}
