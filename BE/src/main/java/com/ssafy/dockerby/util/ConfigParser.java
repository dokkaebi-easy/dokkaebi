package com.ssafy.dockerby.util;

import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig.FrameworkType;
import com.ssafy.dockerby.dto.project.BuildConfigDto;
import com.ssafy.dockerby.dto.project.BuildConfigDto.ConfigProperty;
import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigParser {

  private static Map<String, List<String>> getProperties(BuildConfigDto buildConfigDto) {
    Map<String, List<String>> properties = new HashMap<>();

    List<String> publishes = new ArrayList<>();
    List<String> volumes = new ArrayList<>();
    List<String> envs = new ArrayList<>();
    for (ConfigProperty property : buildConfigDto.getProperties()) {
      switch (property.getProperty()) {
        case "publish":
          publishes.add(property.getFirst() + ":" + property.getSecond());
          break;
        case "volume":
          volumes.add(property.getFirst() + ":" + property.getSecond());
          break;
        case "env":
          envs.add(property.getFirst() + "=" + property.getSecond());
          break;
      }
    }

    if (!publishes.isEmpty()) {
      properties.put("publish", publishes);
    }
    if (!volumes.isEmpty()) {
      properties.put("volume", volumes);
    }
    if (!envs.isEmpty()) {
      properties.put("env", envs);
    }

    return properties;
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
            .useNginx(FrameworkType.valueOf(config.getFrameworkName()), config.getType())
            .build()
        ));

    if (requestDto.getNginxConfig() != null) {
      for (DockerContainerConfig config : configs) {
        if (config.isUseNginx()) {
          if(!config.getProperties().containsKey("publish"))
            config.getProperties().put("publish",new ArrayList<>());
          List<String> publish = config.getProperties().get("publish");
          publish.add("80:80");
          if (requestDto.getNginxConfig().isHttps()) {
            publish.add("443:443");
          }
          break;
        }
      }
    }

    return configs;
  }

}
