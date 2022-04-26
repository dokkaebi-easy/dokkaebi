package com.ssafy.dockerby.core.gitlab;

import com.ssafy.dockerby.core.gitlab.dto.GitlabCloneDto;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitlabAdapter {

  private String regex = "(https?://)(.*)";

  public String getCloneCommand(GitlabCloneDto dto) {
      StringBuilder sb = new StringBuilder();
      sb.append("git clone -b ").append(dto.getBranch())
          .append(" --single-branch ")

  }

  private List<String> parseHttpUrl(String url) {
    List<String> result = new ArrayList<>();
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(url);
    matcher.
  }

}
