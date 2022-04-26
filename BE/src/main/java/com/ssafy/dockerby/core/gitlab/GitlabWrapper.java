package com.ssafy.dockerby.core.gitlab;

import com.ssafy.dockerby.core.gitlab.dto.GitlabWebHookDto;
import java.util.Map;
import javax.ws.rs.NotSupportedException;

public class GitlabWrapper {

  public static GitlabWebHookDto wrap(Map<String, Object> request) {
    String eventType = String.valueOf(request.get("object_kind"));

    if(eventType.equals("push")) {
      String username = String.valueOf(request.get("user_username"));
      String gitHttpUrl = String.valueOf(
          ((Map<String,Object>)request.get("project")).get("git_http_url"));
      String defaultBranch = String.valueOf(
          ((Map<String,Object>)request.get("project")).get("default_branch"));
      return GitlabWebHookDto.of(eventType,username,gitHttpUrl,defaultBranch);

    } else if (eventType.equals("merge_request")) {
      String username = String.valueOf(((Map<String,Object>)request.get("user")).get("username"));
      String gitHttpUrl = String.valueOf(
          ((Map<String,Object>)request.get("project")).get("git_http_url"));
      String defaultBranch = String.valueOf(
          ((Map<String,Object>)request.get("project")).get("default_branch"));
      return GitlabWebHookDto.of(eventType.replace('_',' '),username,gitHttpUrl,defaultBranch);
    } else {
      throw new NotSupportedException();
    }
  }

}
