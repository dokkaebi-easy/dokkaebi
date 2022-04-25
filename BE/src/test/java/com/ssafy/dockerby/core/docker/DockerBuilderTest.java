package com.ssafy.dockerby.core.docker;

import com.mysql.cj.x.protobuf.MysqlxPrepare.PrepareOrBuilder;
import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig.FrameworkType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DockerBuilderTest {

  private DockerBuilder dockerBuilder;

  public DockerBuilderTest() {
    List<String> publishes = new ArrayList<>();
    publishes.add("8080:8080");

    List<String> envs = new ArrayList<>();
    envs.add("MYSQL_ROOT_PASSWORD=ssafy1");

    List<String> volumes = new ArrayList<>();
    volumes.add("/var/dongho : /var/dockerby");
    volumes.add("/var/dongho : /var/dockerby");

    Map<String, List<String>> properties = new HashMap<>();
    properties.put("publish",publishes);
    properties.put("env",envs);
    properties.put("volume",volumes);

    DockerContainerConfig config = DockerContainerConfig.builder()
        .framework(FrameworkType.valueOf("SpringBoot"))
        .version("openjdk:11-jdk")
        .type("Gradle")
        .properties(properties)
        .build();

    this.dockerBuilder = new DockerBuilder("Test");
  }

}