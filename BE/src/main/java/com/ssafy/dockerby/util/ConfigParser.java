package com.ssafy.dockerby.util;

import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig.FrameworkType;
import com.ssafy.dockerby.dto.project.BuildConfigDto;
import com.ssafy.dockerby.dto.project.BuildConfigDto.ConfigProperty;
import com.ssafy.dockerby.dto.project.NginxConfigDto;
import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigParser {

  private static Map<String, List<String>> getProperties(BuildConfigDto buildConfigDto) {
    Map<String, List<String>> properties = new HashMap<>();

    List<String> publishes = new ArrayList<>();
    for (ConfigProperty property : buildConfigDto.getPublish()) {
      publishes.add(property.getFirst() + ":" + property.getSecond());
    }
    if (!publishes.isEmpty()) {
      properties.put("publish", publishes);
    }

    List<String> volumes = new ArrayList<>();
    for (ConfigProperty property : buildConfigDto.getPublish()) {
      volumes.add(property.getFirst() + ":" + property.getSecond());
    }
    if (!volumes.isEmpty()) {
      properties.put("volume", volumes);
    }

    List<String> envs = new ArrayList<>();
    for (ConfigProperty property : buildConfigDto.getPublish()) {
      envs.add(property.getFirst() + "=" + property.getSecond());
    }
    if (!envs.isEmpty()) {
      properties.put("env", envs);
    }

    return properties;
  }

  // TODO
  private static DockerContainerConfig nginxParser(NginxConfigDto nginxConfig) {
    return null;
  }

  public static List<DockerContainerConfig> getBuildConfig(ProjectRequestDto requestDto) {
    List<DockerContainerConfig> configs = new ArrayList<>();
    requestDto.getBuildConfigs().forEach(config ->
        configs.add(DockerContainerConfig.builder()
            .framework(FrameworkType.valueOf(config.getFrameworkName()))
            .name(config.getName())
            .version(config.getVersion())
            .type(config.getType())
            .projectDirectory(config.getProjectDirectory())
            .buildPath(config.getBuildPath())
            .properties(getProperties(config))
            .build()
        ));
    if (requestDto.getNginxConfig() != null) {
      //TODO : null에러 발생
//      configs.add(nginxParser(requestDto.getNginxConfig()));
    }
    return configs;
  }

}
