package com.ssafy.dockerby.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * 프로젝트 관련 경로
 *  /{rootPath}/{projectName}/{PathFolderName}
 *
 *  Volume 관련 경로
 *  /var/dockerby/{projectName}/{volumeFolderName}
 */
public class PathParser {

  @Value("${dockerby.logPath}")
  private static String root;

  @Value("${dockerby.logPath}")
  private static String config;

  @Value("${dockerby.logPath}")
  private static String log;

  @Value("${dockerby.volumePath}")
  private static String volume;

  public static StringBuilder rootPath() {
    StringBuilder sb = new StringBuilder();
    return sb.append(root);
  }

  public static StringBuilder projectPath(String projectName) {
    return rootPath().append("/").append(projectName);
  }

  public static StringBuilder configPath(String projectName) {
    return projectPath(projectName).append("/").append(config);
  }

  public static StringBuilder logPath(String projectName) {
    return projectPath(projectName).append("/").append(log);
  }

  public static StringBuilder repositoryPath(String projectName, Long projectId) {
    return projectPath(projectName).append("/").append(projectId);
  }

  public static StringBuilder volumePath(String projectName, String volumePath) {
    return new StringBuilder(volume).append("/").append(projectName).append("/").append(volumePath);
  }
}
