package com.ssafy.dockerby.core.gitlab;

import com.ssafy.dockerby.core.gitlab.dto.GitlabCloneDto;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitlabAdapter {

  private static String regex = "(https?://)(.*)";

  public static String getCloneCommand(GitlabCloneDto dto) {
    List<String> urls = parseHttpUrl(dto.getGitHttpUrl());
    if(urls.size() != 2)
      throw new IllegalArgumentException("GitlabAdapter.getCloneCommand() Git http url이 잘못된 형식입니다. : " + dto.getGitHttpUrl());
    StringBuilder sb = new StringBuilder();
    sb.append("git clone -b ").append(dto.getBranch())
        .append(" --single-branch ").append(urls.get(0))
        .append("gitlab-ci-token:").append(dto.getAccessToken())
        .append("@").append(urls.get(1));
    return sb.toString();
  }

  public static String getPullCommand(String branchName) {
    StringBuilder sb = new StringBuilder();
    sb.append("git pull origin ").append(branchName);
    return sb.toString();
  }

  public static List<String> parseHttpUrl(String url) {
    List<String> result = new ArrayList<>();
    Matcher matcher = Pattern.compile(regex).matcher(url);
    if(matcher.find()) {
      result.add(matcher.group(1));
      result.add(matcher.group(2));
    }
    return result;
  }

}
