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
    for (BuildConfig config : configs) {
      dockerfileMaker.make(config);
    }
  }

  private String build(BuildConfig config) {
    return dockerCommandMaker.build(config);
  }

  private String run(DockerbyConfig config) {
    return dockerCommandMaker.run(config);
  }

  private String network() {
    return dockerCommandMaker.addBridge();
  }

  private String remove(DockerbyConfig config) {
    return dockerCommandMaker.removeContainer(config);
  }

  public List<String> getBuildCommands(List<BuildConfig> configs) {
    List<String> commands = new ArrayList<>();

    for (BuildConfig config : configs) {
      commands.add(build(config));
    }

    return commands;
  }

  public List<String> getRemoveCommands(List<DockerbyConfig> configs) {
    List<String> commands = new ArrayList<>();

    configs.forEach(config -> commands.add(remove(config)));

    return commands;
  }

  public List<String> getRunCommands(List<DockerbyConfig> configs) {
    List<String> commands = new ArrayList<>();
    commands.add(network());

    configs.forEach(config -> commands.add(run(config)));

    return commands;
  }

}
