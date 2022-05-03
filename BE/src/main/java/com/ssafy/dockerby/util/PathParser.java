package com.ssafy.dockerby.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 프로젝트 관련 경로
 *  /{rootPath}/{projectName}/{PathFolderName}
 *
 *  Volume 관련 경로
 *  /var/dockerby/{projectName}/{volumeFolderName}
 */
@Component
public class PathParser {

  @Value("${dockerby.rootPath}")
  private String root;

  @Value("${dockerby.configPath}")
  private String config;

  @Value("${dockerby.logPath}")
  private String log;

  @Value("${dockerby.volumePath}")
  private String volume;

  public StringBuilder rootPath() {
    StringBuilder sb = new StringBuilder();
    return sb.append(root);
  }

  public StringBuilder projectPath(String projectName) {
    return rootPath().append("/").append(projectName);
  }

  public StringBuilder configPath(String projectName) {
    return projectPath(projectName).append("/").append(config);
  }

  public StringBuilder logPath(String projectName) {
    return projectPath(projectName).append("/").append(log);
  }

  public StringBuilder repositoryPath(String projectName, Long projectId) {
    return projectPath(projectName).append("/").append(projectId);
  }

  public StringBuilder volumePath(String projectName, String volumePath) {
    return new StringBuilder(volume).append("/").append(projectName).append("/").append(volumePath);
  }
}
