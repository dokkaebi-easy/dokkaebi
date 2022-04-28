package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.dto.DockerContainerConfig;
import com.ssafy.dockerby.util.FileManager;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DockerfileMaker {

  private final String rootDir;

  public DockerfileMaker(String rootDir) {
    this.rootDir = rootDir;
  }

  /**
   * 환경설정 값을 이용해 Dockerfile을 생성해준다.
   * MySQL은 Dockerfile을 생성하지 않아 바로 break 된다.
   * @param config : Framework/Library 환경설정 값
   */
  public void make(DockerContainerConfig config) throws IOException {
    switch (config.getFramework()) {
      case Vue:
        makeVueWithNginxDockerFile(config);
        break;
      case SpringBoot:
        makeSpringBootDockerfile(config);
        break;
      case Django:
        makeDjangoDockerfile(config);
        break;
      case React:
        makeReactWithNginxDockerFile(config);
        break;
      case MySQL:
        // MySQL은 dockerfile 없이 docker run으로 바로 진행
        break;
      default:
        throw new IllegalArgumentException("makeDockerFile : " + config.getFramework() + "Error");
    }
  }

  private void makeSpringBootDockerfile(DockerContainerConfig config) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("FROM ").append(config.getVersion()).append(' ').append("as builder").append('\n');
    sb.append("COPY . . \n");
    if (config.getType().equals("Gradle")) {
      sb.append("RUN ").append("chmod +x ./gradlew").append('\n');
      sb.append("RUN ").append("./gradlew clean build").append('\n');
    } else if (config.getType().equals("Maven")) {
      sb.append("RUN ").append("chmod +x ./mvnw").append('\n');
      sb.append("RUN ").append("./mvnw clean package").append('\n');
    }
    sb.append("FROM ").append(config.getVersion()).append('\n');
    sb.append("COPY --from=builder ");
    if (config.getType().equals("Gradle")) {
      sb.append(((config.getBuildPath().isBlank()) ? "/build/libs" : config.getBuildPath()) + "/*.jar");
    } else if (config.getType().equals("Maven")) {
      sb.append(((config.getBuildPath().isBlank()) ? "/target" : config.getBuildPath()) + "/*.jar");
    }
    sb.append(" app.jar").append('\n');
    sb.append("ENTRYPOINT [\"java\", \"-jar\", \"./app.jar\"]");

    saveDockerFile(getDestPath(config.getProjectDirectory()),sb.toString());
  }

  private void makeReactWithNginxDockerFile(DockerContainerConfig config) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("FROM ").append(config.getVersion()).append(' ').append("as builder").append('\n');
    sb.append("COPY . . \n");

    sb.append("RUN ").append("npm install").append('\n');
    sb.append("RUN ").append("npm run build").append('\n');

    sb.append("FROM ").append("nginx:1.18.0").append('\n');
    sb.append("COPY ./default.conf /etc/nginx/conf.d/default.conf\n");
    sb.append("COPY --from=builder ");
    sb.append((config.getBuildPath().isBlank()) ? "/build" : config.getBuildPath())
        .append(" /usr/share/nginx/html\n");
    sb.append("EXPOSE ").append("3000").append('\n');
    sb.append("CMD [\"nginx\", \"-g\", \"daemon off;\"]");

    saveDockerFile(getDestPath(config.getProjectDirectory()),sb.toString());
  }

  private void makeVueWithNginxDockerFile(DockerContainerConfig config) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("FROM ").append(config.getVersion()).append(' ').append("as builder").append('\n');
    sb.append("COPY . . \n");

    sb.append("RUN ").append("npm install").append('\n');
    sb.append("RUN ").append("npm run build").append('\n');

    sb.append("FROM ").append("nginx:1.18.0").append('\n');
    sb.append("COPY ./default.conf /etc/nginx/conf.d/default.conf\n");
    sb.append("COPY --from=builder ");
    sb.append((config.getBuildPath().isBlank()) ? "/dist" : config.getBuildPath())
      .append(" /usr/share/nginx/html\n");
    sb.append("EXPOSE ").append("3000").append('\n');
    sb.append("CMD [\"nginx\", \"-g\", \"daemon off;\"]");

    saveDockerFile(getDestPath(config.getProjectDirectory()),sb.toString());
  }

  private void makeDjangoDockerfile(DockerContainerConfig config) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("FROM ").append(config.getVersion()).append(' ').append("as builder").append('\n');

    sb.append("WORKDIR ").append("/usr/src/app").append('\n');

    sb.append("ENV ").append("PYTHONDONTWRITEBYTECODE 1").append('\n');
    sb.append("ENV ").append("PYTHONUNBUFFERED 1").append('\n');

    sb.append("COPY ./requirements.txt /usr/src/app").append('\n');

    sb.append("RUN ").append("pip install --upgrade pip").append('\n');

    sb.append("COPY ").append(". /usr/src/app").append('\n');

    sb.append("RUN ").append("apk update").append('\n');
    sb.append("RUN ").append("apk add make automake gcc g++ subversion python3-dev").append('\n');

    sb.append("RUN ").append("pip install -r requirements.txt").append('\n');

    sb.append("CMD [\"python\", \"manage.py\", \"runserver\", \"0.0.0.0:8000\"]");

    saveDockerFile(getDestPath(config.getProjectDirectory()),sb.toString());
  }

  private String getDestPath(String projectDirectory) {
    StringBuilder path = new StringBuilder();
    path.append(rootDir).append(projectDirectory);
    return path.toString();
  }

  private void saveDockerFile(String pjtDir, String sb) throws IOException {
      FileManager.saveFile(pjtDir, "dockerfile", sb);
  }
}
