package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.vo.docker.BuildConfig;
import com.ssafy.dockerby.core.docker.vo.docker.DockerbyConfig;
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

  public void saveDockerfile(BuildConfig config) throws IOException {
    dockerfileMaker.make(config);
  }

  public void saveDockerfiles(List<BuildConfig> configs) throws IOException {
    log.info("saveDockerfiles Start");
    for (BuildConfig config : configs) {
      dockerfileMaker.make(config);
    }
    log.info("saveDockerfiles Done");
  }

  private String build(BuildConfig config) {
    log.info("build Start");
    return dockerCommandMaker.build(config);
  }

  private String run(DockerbyConfig config) {
    log.info("run Start");
    return dockerCommandMaker.run(config);
  }

  private String network() {
    log.info("network Start");
    return dockerCommandMaker.addBridge();
  }

  private String remove(DockerbyConfig config) {
    log.info("remove Start");
    return dockerCommandMaker.removeContainer(config);
  }

  public List<String> getBuildCommands(List<BuildConfig> configs) {
    log.info("getBuildCommands Start");
    List<String> commands = new ArrayList<>();

    for (BuildConfig config : configs) {
      commands.add(build(config));
    }
    log.info("getBuildCommands Done");
    return commands;
  }

  public List<String> getRemoveCommands(List<? extends DockerbyConfig> configs) {
    log.info("getRemoveCommands Start");
    List<String> commands = new ArrayList<>();

    configs.forEach(config -> commands.add(remove(config)));

    log.info("getRemoveCommands Done");
    return commands;
  }

  public List<String> getRunCommands(List<? extends DockerbyConfig> configs) {
    log.info("getRunCommands Start");
    List<String> commands = new ArrayList<>();
    commands.add(network());

    configs.forEach(config -> commands.add(run(config)));

    log.info("getRunCommands Done");
    return commands;
  }

}
