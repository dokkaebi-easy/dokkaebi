package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DockerAdapter {

  private final DockerfileMaker dockerfileMaker;
  private final DockerCommandMaker dockerCommandMaker;

  public DockerAdapter(String projectPath, String projectName) {
    this.dockerfileMaker = new DockerfileMaker(projectPath);
    this.dockerCommandMaker = new DockerCommandMaker(projectPath, projectName);
  }

  //요거 안씀
  public void saveDockerfile(DockerContainerConfig config) throws IOException {
    dockerfileMaker.make(config);
  }

  public void saveDockerfiles(List<DockerContainerConfig> configs) throws IOException {
    log.info("saveDockerfiles Start");
    for (DockerContainerConfig config : configs) {
      dockerfileMaker.make(config);
    }
    log.info("saveDockerfiles Done");
  }

  private String build(DockerContainerConfig config) {
    log.info("build Start");
    return dockerCommandMaker.build(config);
  }

  private String run(DockerContainerConfig config) {
    log.info("run Start");
    return dockerCommandMaker.run(config);
  }

  private String network() {
    log.info("network Start");
    return dockerCommandMaker.bridge();
  }

  private String remove(DockerContainerConfig config) {
    log.info("remove Start");
    return dockerCommandMaker.removeContainer(config);
  }

  public List<String> getBuildCommands(List<DockerContainerConfig> configs) {
    log.info("getBuildCommands Start");
    List<String> commands = new ArrayList<>();
    commands.add(network());

    for (DockerContainerConfig config : configs) {
      if (config.buildPossible()) {
        commands.add(build(config));
      }
    }
    log.info("getBuildCommands Done");
    return commands;
  }

  public List<String> getRemoveCommands(List<DockerContainerConfig> configs) {
    log.info("getRemoveCommands Start");
    List<String> commands = new ArrayList<>();
    commands.add(network());

    configs.forEach(config -> commands.add(remove(config)));

    log.info("getRemoveCommands Done");
    return commands;
  }

  public List<String> getRunCommands(List<DockerContainerConfig> configs) {
    log.info("getRunCommands Start");
    List<String> commands = new ArrayList<>();
    commands.add(network());

    configs.forEach(config -> commands.add(run(config)));

    log.info("getRunCommands Done");
    return commands;
  }

  //요거 안씀
  public List<String> getBuildAndRun(List<DockerContainerConfig> configs) {
    List<String> commands = new ArrayList<>();
    commands.add(network());

    List<String> buildCommands = new ArrayList<>();
    List<String> runCommands = new ArrayList<>();
    for (DockerContainerConfig config : configs) {
      if (config.buildPossible()) {
        buildCommands.add(build(config));
      }
      runCommands.add(run(config));
    }

    commands.addAll(buildCommands);
    commands.addAll(runCommands);

    return commands;
  }
}
