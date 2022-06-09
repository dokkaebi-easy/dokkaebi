package com.dokkaebi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 프로젝트 관련 경로
 *  /{rootPath}/{projectName}/{PathFolderName}
 *
 *  Volume 관련 경로
 *  /var/dokkaebi/{projectName}/{volumeFolderName}
 */
@Component
public class PathParser {

  @Value("${dokkaebi.rootPath}")
  private String root;

  @Value("${dokkaebi.configPath}")
  private String config;

  @Value("${dokkaebi.logPath}")
  private String log;

  @Value("${dokkaebi.volumePath}")
  private String volume;

  @Value("${dokkaebi.dokkaebiConfigPath}")
  private String dokkaebiConfig;

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
    return volumePath().append("/").append(projectName).append("/").append(volumePath);
  }

  public StringBuilder volumePath() {
    return new StringBuilder(volume);
  }

  public StringBuilder dokkaebiConfigPath() {
    return new StringBuilder(dokkaebiConfig);
  }
}
