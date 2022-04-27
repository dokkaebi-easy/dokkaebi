package com.ssafy.dockerby.core.gitlab;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GitlabAdapterTest {

  @Test
  public void git_http_url_parse_test() {
    // given
    String https = "https://lab.ssafy.com/s06-final/S06P31S205.git";
    String http = "http://lab.ssafy.com/s06-final/S06P31S205.git";

    // when
    List<String> httpsResult = GitlabAdapter.parseHttpUrl(https);
    List<String> httpResult = GitlabAdapter.parseHttpUrl(http);
    // then
    Assertions.assertEquals(httpsResult.size(),2);
    Assertions.assertEquals(httpsResult.get(0),"https://");
    Assertions.assertEquals(httpsResult.get(1),"lab.ssafy.com/s06-final/S06P31S205.git");

    Assertions.assertEquals(httpResult.size(),2);
    Assertions.assertEquals(httpResult.get(0),"http://");
    Assertions.assertEquals(httpResult.get(1),"lab.ssafy.com/s06-final/S06P31S205.git");
  }

}
