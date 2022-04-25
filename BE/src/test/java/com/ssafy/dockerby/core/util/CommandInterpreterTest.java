package com.ssafy.dockerby.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CommandInterpreterTest {

  @Test
  public void ExecTest() throws IOException {
    // given
    List<String> commands = new ArrayList<>();
//    commands.add("docker network create dockerby_network");
//    commands.add("docker run -d --name dockerby_db --network dockerby_network --publish 8483:3306 --env MYSQL_ROOT_PASSWORD=404c8f50fe11467b834410744ebf9695 --env MYSQL_DATABASE=dockerby --volume D:\\SSAFY\\Dockerby\\S06P31S205\\BE\\DB:/docker-entrypoint-initdb.d/ mysql:8.0.28");
//    commands.add("docker run -d --name dockerby -p 8482:8080 --network dockerby_network -e SPRING_DATASOURCE_URL=jdbc:mysql://dockerby_db:8483/dockerby?useSSL=false&serverTimezone=UTC&autoReconnect=true -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=404c8f50fe11467b834410744ebf9695 dockerby:latest");
//    commands.add("git clone -b master --single-branch https://gitlab-ci-token:qUpsyG1eYZvfb3tqfiis@lab.ssafy.com/S06-final/S06P31S205.git");
    commands.add("git pull origin master");
    // when
    CommandInterpreter.run(".","dockerby",1,commands);

    // then
  }

}