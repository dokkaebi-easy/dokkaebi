package com.ssafy.dockerby.core.util;

import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig.FrameworkType;
import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig.PropertyValue;
import com.ssafy.dockerby.dto.project.BuildConfigDto;
import com.ssafy.dockerby.dto.project.ConfigProperty;
import com.ssafy.dockerby.dto.project.ProjectConfigDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigParser {

  private static Map<String, List<PropertyValue>> getProperties(BuildConfigDto buildConfigDto) {
    Map<String, List<PropertyValue>> properties = new HashMap<>();

    List<PropertyValue> publishes = new ArrayList<>();
    List<PropertyValue> volumes = new ArrayList<>();

    for (ConfigProperty property : buildConfigDto.getProperties()) {
      switch (property.getProperty()) {
        case "publish":
          publishes.add(new PropertyValue(property.getData(), property.getData()));
          break;
        case "volume":
          volumes.add(new PropertyValue(property.getData(), property.getData()));
          break;
      }
    }

    if (!publishes.isEmpty()) {
      properties.put("publish", publishes);
    }
    if (!volumes.isEmpty()) {
      properties.put("volume", volumes);
    }

    return properties;
  }

  /**
   * List<{@link BuildConfigDto}>를 {@link DockerContainerConfig}로 변환해준다.
   * {@link BuildConfigDto#frameworkId}를 {@link FrameworkType}으로 변환한다.
   *
   * @param requestDto
   * @return {@link DockerContainerConfig}
   */
  public static List<DockerContainerConfig> getBuildConfig(ProjectConfigDto requestDto) {
    List<DockerContainerConfig> configs = new ArrayList<>();
    requestDto.getBuildConfigs().forEach(config ->
        configs.add(DockerContainerConfig.builder()
            .framework(FrameworkType.from(config.getFrameworkId().intValue()))
            .name(config.getName())
            .version(config.getVersion())
            .type(config.getType())
            .projectDirectory(config.getProjectDirectory())
            .buildPath(config.getBuildPath())
            .properties(getProperties(config))
            .useNginx(FrameworkType.from(config.getFrameworkId().intValue()), config.getType())
            .build()
        ));

    if (requestDto.getNginxConfig() != null) {
      for (DockerContainerConfig config : configs) {
        if (config.isUseNginx()) {
          if (!config.getProperties().containsKey("publish")) {
            config.getProperties().put("publish", new ArrayList<>());
          }
          List<PropertyValue> publish = config.getProperties().get("publish");
          publish.add(new PropertyValue("80", "80"));
          if (requestDto.getNginxConfig().isHttps()) {
            publish.add(new PropertyValue("443", "443"));
          }
          break;
        }
      }
    }

    return configs;
  }

  public static List<ConfigProperty> dockerContainerPropertyToConfigProperty(
      Map<String, List<PropertyValue>> properties) {
    List<ConfigProperty> result = new ArrayList<>();

    for (String key : properties.keySet()) {
      List<PropertyValue> strings = properties.get(key);
      strings.forEach(property -> result.add(ConfigProperty.of(key, property.getHost())));
    }
    return result;
  }

}
