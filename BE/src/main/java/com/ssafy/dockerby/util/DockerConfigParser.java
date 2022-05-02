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

public class DockerConfigParser {

  public DbConfig DbConverter(String name, String framework, String dockerVersion,
      List<DockerbyProperty> properties, String dumpLocation) {

    return new DbConfig(name, framework, dockerVersion, properties, dumpLocation);
  }

  public BuildConfig buildConverter(String name, String framework, String dockerVersion,
      List<DockerbyProperty> properties, String projectDirectory, String buildPath, String type) {
    return new BuildConfig(name, framework, dockerVersion, properties, projectDirectory, buildPath,
        type);
  }

  public NginxConfig nginxConverter(NginxConfigDto dto) {
    return new NginxConfig(dto.getDomains(), dto.getLocations(), dto.isHttps(),
        dto.getHttpsOption(), 50);
  }

  public List<DockerbyProperty> propertiesConverter (List<ConfigProperty> properties) {
    List<DockerbyProperty> newProperties = new ArrayList<>();
    for (ConfigProperty property : properties) {
      newProperties.add(new DockerbyProperty(property.getProperty(), property.getData(),
          property.getData()));
    }
    return newProperties;
  }

  public List<DockerbyProperty> propertyConverter(ConfigProperty property) {
    List<DockerbyProperty> newProperties = new ArrayList<>();
    newProperties.add(new DockerbyProperty(property.getProperty(), property.getData(),
        property.getData()));
    return newProperties;
  }

  public List<BuildConfig> buildsConverter(List<BuildConfigDto> dtos) {
    List<BuildConfig> configs = new ArrayList<>();
    for (BuildConfigDto dto : dtos) {
      if (dto.getName().isBlank()) {
        continue;
      }
      if (dto.getFrameworkId() == -1) {
        continue;
      }

      configs.add(new BuildConfig(dto.getName(), null, null,
          propertiesConverter(dto.getProperties()), dto.getProjectDirectory(),
          dto.getBuildPath(), dto.getType()));

    }
    return configs;
  }


}
