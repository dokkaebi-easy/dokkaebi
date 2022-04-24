package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.dto.ContainerConfig;
import com.ssafy.dockerby.core.docker.dto.ContainerConfig.FrameworkType;

class DockerBuilderTest {

  private DockerBuilder dockerBuilder;

  public DockerBuilderTest() {
    ContainerConfig config = ContainerConfig.builder()
        .framework(FrameworkType.valueOf("SpringBoot"))
        .version("openjdk:11-jdk")
        .type("Gradle")
        .properties("port","8080:8080")
        .properties("volume","/var/dongho : /var/dockerby")
        .properties("volume","/var/dongho : /var/dockerby")
        .properties("env","MYSQL_ROOT_PASSWORD=ssafy1")
        .build();
    this.dockerBuilder = new DockerBuilder("Test",config);
  }

}