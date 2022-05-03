package com.ssafy.dockerby.util;

import com.ssafy.dockerby.core.docker.vo.docker.BuildConfig;
import com.ssafy.dockerby.core.docker.vo.docker.DbConfig;
import com.ssafy.dockerby.core.docker.vo.docker.DockerbyProperty;
import com.ssafy.dockerby.core.docker.vo.nginx.NginxConfig;
import com.ssafy.dockerby.dto.project.BuildConfigDto;
import com.ssafy.dockerby.dto.project.ConfigProperty;
import com.ssafy.dockerby.dto.project.NginxConfigDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class DockerConfigParser {

  private final PathParser pathParser;

  public DbConfig DbConverter(String name, String framework, String dockerVersion,
      List<DockerbyProperty> properties, String dumpLocation, String projectName) {
    log.info("DbConverter Start : framework = {} , dockerVersion = {}", framework, dockerVersion);

    DbConfig dbConfig = new DbConfig(name, framework, dockerVersion, properties, dumpLocation);
    if(!dumpLocation.isBlank()) {
      String volumePath = pathParser.volumePath(projectName, dumpLocation).toString();
      dbConfig.addProperty(new DockerbyProperty("volume", volumePath, dumpLocation));
    }
    log.info("DbConverter Done");
    return dbConfig;
  }

  public BuildConfig buildConverter(String name, String framework, String dockerVersion,
      List<DockerbyProperty> properties, String projectDirectory, String buildPath, String type) {
    log.info("buildConverter Start : framework = {} , dockerVersion = {}", framework, dockerVersion);
    return new BuildConfig(name, framework, dockerVersion, properties, projectDirectory, buildPath,
        type);
  }

  public NginxConfig nginxConverter(NginxConfigDto dto) {
    log.info("nginxConverter Start");
    return new NginxConfig(dto.getDomains(), dto.getLocations(), dto.isHttps(),
        dto.getHttpsOption(), 50);
  }

  public List<DockerbyProperty> dockerbyProperties(List<ConfigProperty> properties) {
    log.info("dockerbyProperties Start");
    List<DockerbyProperty> newProperties = new ArrayList<>();
    for (ConfigProperty property : properties) {
      newProperties.add(new DockerbyProperty(property.getProperty(), property.getData(),
          property.getData()));
    }
    return newProperties;
  }

  //사용안됨
  public List<DockerbyProperty> dockerbyProperty(ConfigProperty property) {
    List<DockerbyProperty> newProperties = new ArrayList<>();
    newProperties.add(new DockerbyProperty(property.getProperty(), property.getData(),
        property.getData()));
    return newProperties;
  }

  public List<ConfigProperty> configProperties(List<DockerbyProperty> properties) {
    log.info("configProperties Start");
    List<ConfigProperty> newProperties = new ArrayList<>();
    for (DockerbyProperty property : properties) {
      newProperties.add(ConfigProperty.of(property.getType(), property.getHost()));
    }
    return newProperties;
  }

  //사용안됨
  public List<BuildConfig> buildsConverter(List<BuildConfigDto> dtos) {
    List<BuildConfig> configs = new ArrayList<>();
    for (BuildConfigDto dto : dtos) {
      if (dto.getName().isBlank()) {
        continue;
      }
      if (dto.getFrameworkId() == 0) {
        continue;
      }

      configs.add(new BuildConfig(dto.getName(), null, null,
          dockerbyProperties(dto.getProperties()), dto.getProjectDirectory(),
          dto.getBuildPath(), dto.getType()));

    }
    return configs;
  }
}
