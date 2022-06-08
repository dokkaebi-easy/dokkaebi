package com.ssafy.dockerby.core.docker;

import com.ssafy.dockerby.core.docker.vo.docker.BuildConfig;
import com.ssafy.dockerby.core.docker.vo.docker.DockerbyConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DockerCommandMaker {

    private final String projectName;

    private final String projectPath;

    private String networkBridge;

    public DockerCommandMaker(String projectPath, String projectName) {
        this.projectName = projectName;
        this.projectPath = projectPath;
    }

    public String build(BuildConfig config) {
        log.info("build Start");
        StringBuilder sb = new StringBuilder();
        // TODO : Image tag를 latest로 하는 것은 권장되지 않습니다.
        sb.append("docker build -t ")
            .append(projectName).append('-').append(config.getName()).append(":latest")
            .append(' ').append(projectPath).append(config.getProjectDirectory());
        log.info("build Done");
        return sb.toString();
    }

    public String runWithVersion(DockerbyConfig config) {
        log.info("run Start {}", config);
        StringBuilder sb = new StringBuilder();
        sb.append("docker run -d --name ")
            .append(projectName).append('-').append(config.getName()).append(' ');

        for (String command : config.propertyCommands()) {
            sb.append(command).append(' ');
        }

        if (this.networkBridge == null) {
            setBridge();
        }
        sb.append(" --network ").append(this.networkBridge);

        sb.append(' ').append(config.getVersion());

        log.info("run Done");
        return sb.toString();
    }

    public String run(DockerbyConfig config) {
        log.info("run Start {}", config);
        StringBuilder sb = new StringBuilder();
        sb.append("docker run -d --name ")
            .append(projectName).append('-').append(config.getName()).append(' ');

        for (String command : config.propertyCommands()) {
            sb.append(command).append(' ');
        }

        if (this.networkBridge == null) {
            setBridge();
        }
        sb.append(" --network ").append(this.networkBridge);

        // TODO : Image tag를 latest로 하는 것은 권장되지 않습니다.
        sb.append(' ').append(projectName).append('-').append(config.getName()).append(":latest");

        log.info("run Done");
        return sb.toString();
    }

    public String removeBridge() {
        log.info("removeBridge Start");
        if (this.networkBridge == null) {
            this.networkBridge = projectName + "_bridge";
        }
        log.info("removeBridge Done");
        return "docker network rm " + this.networkBridge;
    }

    public String addBridge() {
        log.info("addBridge Start");
        if (this.networkBridge == null) {
            this.networkBridge = projectName + "_bridge";
        }
        log.info("addBridge Done");
        return "docker network create " + this.networkBridge;
    }

    private void setBridge() {
        this.networkBridge = projectName + "_bridge";
    }

    public String removeContainer(DockerbyConfig config) {
        log.info("removeContainer Start");
        StringBuilder sb = new StringBuilder();
        sb.append("/home/conf/remove.sh ").append(projectName).append('-').append(config.getName());
        log.info("removeContainer Done");
        return sb.toString();
    }

    public String stopContainer(DockerbyConfig config) {
        log.info("stopContainer Start");
        StringBuilder sb = new StringBuilder();
        sb.append("docker stop ").append(projectName).append('-').append(config.getName());
        log.info("stopContainer Done");
        return sb.toString();
    }
}
