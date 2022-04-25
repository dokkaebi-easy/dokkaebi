package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DockerBuilder {

  private final String rootDir;
  private final DockerfileMaker dockerfileMaker;
  private final DockerCommandMaker dockerCommandMaker;

  public DockerBuilder(String projectName) {
    StringBuilder sb = new StringBuilder();
    sb.append("/home/").append(projectName).append("/");
    this.rootDir = sb.toString();
    this.dockerfileMaker = new DockerfileMaker(this.rootDir);
    this.dockerCommandMaker = new DockerCommandMaker(projectName, this.rootDir);
  }

  public void saveDockerfile(DockerContainerConfig config) throws IOException {
    dockerfileMaker.make(config);
  }

  public void saveDockerfiles(List<DockerContainerConfig> configs) throws IOException{
    for(DockerContainerConfig config : configs)
      dockerfileMaker.make(config);
  }

  private String build(DockerContainerConfig config) {
    return dockerCommandMaker.build(config);
  }

  private String run(DockerContainerConfig config) {
    return dockerCommandMaker.run(config);
  }

  private String network() {
    return dockerCommandMaker.bridge();
  }

  private String removeContainer(DockerContainerConfig config) {
    return dockerCommandMaker.removeContainer(config);
  }

  public List<String> getBuildCommands(List<DockerContainerConfig> configs) {
    List<String> commands = new ArrayList<>();
    commands.add(network());

    configs.forEach(config -> commands.add(build(config)));

    return commands;
  }

  public List<String> getRunCommands(List<DockerContainerConfig> configs) {
    List<String> commands = new ArrayList<>();
    commands.add(network());

    configs.forEach(config -> commands.add(run(config)));

    return commands;
  }

  public List<String> getBuildAndRun(List<DockerContainerConfig> configs) {
    List<String> commands = new ArrayList<>();
    commands.add(network());

    List<String> buildCommands = new ArrayList<>();
    List<String> runCommands = new ArrayList<>();
    configs.forEach(config -> {
      buildCommands.add(build(config));
      runCommands.add(run(config));
    });

    commands.addAll(buildCommands);
    commands.addAll(runCommands);

    return commands;
  }
}
