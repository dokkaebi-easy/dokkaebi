package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.vo.docker.BuildConfig;
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
  public void make(BuildConfig config) throws IOException {
    String framework = config.getFramework();
    if("Vue".equals(framework)) {
      if("Yes".equals(config.getType()))
        makeVueWithNginxDockerFile(config);
    } else if ("React".equals(framework)) {
      if("Yes".equals(config.getType()))
        makeReactWithNginxDockerFile(config);
    } else if ("Next".equals(framework)) {
      makeNextDockerfile(config);
    } else if ("Django".equals(framework)) {
      makeDjangoDockerfile(config);
    } else if ("SpringBoot".equals(framework)) {
      makeSpringBootDockerfile(config);
    } else {
      throw new IllegalArgumentException(framework);
    }
  }

  private void makeNextDockerfile(BuildConfig config) throws IOException {
    log.info("makeNextDockerfile Start");
    StringBuilder sb = new StringBuilder();
    sb.append("FROM ").append(config.getVersion()).append('\n');
    sb.append("COPY . . \n");
    sb.append("RUN ").append("npm install").append('\n');
    sb.append("RUN ").append("npm run build").append('\n');
    sb.append("CMD [\"npm\", \"run\", \"start\"]");
    saveDockerFile(getDestPath(config.getProjectDirectory()),sb.toString());
    log.info("makeNextDockerfile Done");
  }

  private void makeSpringBootDockerfile(BuildConfig config) throws IOException {
    log.info("makeSpringBootDockerfile Start");
    StringBuilder sb = new StringBuilder();
    sb.append("FROM ").append(config.getVersion()).append(' ').append("as builder").append('\n');
    sb.append("COPY . . \n");
    if ("Gradle".equals(config.getType())) {
      sb.append("RUN ").append("chmod +x ./gradlew").append('\n');
      sb.append("RUN ").append("./gradlew clean build").append('\n');
    } else if ("Maven".equals(config.getType())) {
      sb.append("RUN ").append("chmod +x ./mvnw").append('\n');
      sb.append("RUN ").append("./mvnw clean package").append('\n');
    }
    sb.append("FROM ").append(config.getVersion()).append('\n');
    sb.append("COPY --from=builder ");
    if ("Gradle".equals(config.getType())) {
      sb.append(((config.getBuildPath().isBlank()) ? "/build/libs" : config.getBuildPath()) + "/*.jar");
    } else if ("Maven".equals(config.getType())) {
      sb.append(((config.getBuildPath().isBlank()) ? "/target" : config.getBuildPath()) + "/*.jar");
    }
    sb.append(" /app.jar").append('\n');
    sb.append("ENTRYPOINT [\"java\", \"-jar\", \"/app.jar\"]");

    saveDockerFile(getDestPath(config.getProjectDirectory()),sb.toString());
    log.info("makeSpringBootDockerfile Done");
  }

  private void makeReactWithNginxDockerFile(BuildConfig config) throws IOException {
    log.info("makeReactWithNginxDockerFile Start");
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

    sb.append("CMD [\"nginx\", \"-g\", \"daemon off;\"]");

    saveDockerFile(getDestPath(config.getProjectDirectory()),sb.toString());
    log.info("makeReactWithNginxDockerFile Done");
  }

  private void makeVueWithNginxDockerFile(BuildConfig config) throws IOException {
    log.info("makeVueWithNginxDockerFile Start");
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

    sb.append("CMD [\"nginx\", \"-g\", \"daemon off;\"]");

    saveDockerFile(getDestPath(config.getProjectDirectory()),sb.toString());
    log.info("makeVueWithNginxDockerFile Done");
  }

  private void makeDjangoDockerfile(BuildConfig config) throws IOException {
    log.info("makeDjangoDockerfile Start");
    StringBuilder sb = new StringBuilder();
    sb.append("FROM ").append(config.getVersion()).append(' ').append("as builder").append('\n');

    sb.append("WORKDIR ").append("/usr/src/app").append('\n');

    sb.append("ENV ").append("PYTHONDONTWRITEBYTECODE 1").append('\n');
    sb.append("ENV ").append("PYTHONUNBUFFERED 1").append('\n');

    sb.append("COPY ./requirements.txt /usr/src/app").append('\n');

    sb.append("RUN ").append("pip install --upgrade pip").append('\n');

    sb.append("COPY ").append(". /usr/src/app").append('\n');

    sb.append("RUN ").append("pip install -r requirements.txt").append('\n');
    sb.append("RUN ").append("python manage.py makemigrations").append('\n');
    sb.append("RUN ").append("python manage.py migrate").append('\n');
    sb.append("CMD [\"python\", \"manage.py\", \"runserver\", \"0.0.0.0:8000\"]");

    saveDockerFile(getDestPath(config.getProjectDirectory()),sb.toString());
    log.info("makeDjangoDockerfile Done");
  }

  private String getDestPath(String projectDirectory) {
    log.info("getDestPath Start");
    StringBuilder path = new StringBuilder();
    path.append(rootDir).append(projectDirectory);
    log.info("getDestPath Done : path = {}",path.toString());
    return path.toString();
  }

  private void saveDockerFile(String pjtDir, String sb) throws IOException {
    log.info("saveDockerFile Start");
      FileManager.saveFile(pjtDir, "Dockerfile", sb);
    log.info("saveDockerFile Done");
  }
}
