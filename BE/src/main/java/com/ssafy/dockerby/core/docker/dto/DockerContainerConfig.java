package com.ssafy.dockerby.core.docker.dto;

import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DockerContainerConfig {

  // 프레임워크 혹은 라이브러리
  // Ex) SpringBoot, Vue, React, Django, MySQL...
  private FrameworkType framework;

  // 프레임워크/라이브러리 프로젝트 이름 or 해당 프로세스 별칭
  private String name;

  // 빌드할 언어 버전
  // Docker hub official image tag
  private String version;

  // Option : Spring Boot의 경우 Gradle, Maven
  private String type;

  // Build할 프로젝트 디렉토리 위치
  @Builder.Default
  private String projectDirectory = ".";

  // Build path (Option) : 사용자가 임의로 Build output directory path를 변경한 경우
  private String buildPath;

  // Build 속성 들 (port, volume, env, buildPath...)
  private Map<String, List<PropertyValue>> properties;

  private boolean useNginx;

  public boolean buildPossible() {
    return !(framework == FrameworkType.None || framework == FrameworkType.Django);
  }

  public enum FrameworkType {
    None(0), SpringBoot(1), Vue(2), React(3), Next(4), Django(5), MySQL(6);

    private final int value;

    private FrameworkType(int value) {
      this.value = value;
    }

    public String toString() {
      return String.valueOf(this.value);
    }

    public static FrameworkType from(int value) {
      if (value == FrameworkType.SpringBoot.ordinal()) {
        return FrameworkType.SpringBoot;
      } else if (value == FrameworkType.Vue.ordinal()) {
        return FrameworkType.Vue;
      } else if (value == FrameworkType.React.ordinal()) {
        return FrameworkType.React;
      } else if (value == FrameworkType.Next.ordinal()) {
        return FrameworkType.Next;
      } else if (value == FrameworkType.Django.ordinal()) {
        return FrameworkType.Django;
      } else if (value == FrameworkType.MySQL.ordinal()) {
        return FrameworkType.MySQL;
      } else {
        return FrameworkType.None;
      }
    }
  }

  public static class DockerContainerConfigBuilder {

    public DockerContainerConfigBuilder useNginx(FrameworkType frameworkType, String type) {
      if (type != null) {
        if (frameworkType == FrameworkType.React && type.equals("Yes")
            || frameworkType == FrameworkType.Vue && type.equals("Yes")) {
          useNginx = true;
          return this;
        }
      }
      useNginx = false;
      return this;
    }

  }

  public void convertVersion(String docekerHubVersion) {
    this.version = docekerHubVersion;
  }

  @Getter
  @AllArgsConstructor
  public static class PropertyValue {
    private final String host;
    private final String container;

    public String getPropertyCommand(String propertyType) {
      if(propertyType.equals("publish"))
        return new StringBuilder().append("-p ").append(host).append(":").append(container).toString();
      else if (propertyType.equals("volume"))
        return new StringBuilder().append("-v ").append(host).append(":").append(container).toString();
      else if (propertyType.equals("env"))
        return new StringBuilder().append("-e ").append(host).append("=").append(container).toString();
      else
        throw new IllegalArgumentException();
    }
  }
}
