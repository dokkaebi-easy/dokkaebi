package com.ssafy.dockerby;

import com.ssafy.dockerby.core.gitlab.GitlabAccess;
import org.gitlab4j.api.GitLabApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DockerbyApplication {
	public static void main(String[] args) {
		SpringApplication.run(DockerbyApplication.class, args);




		/*
			// GitLab 서버 통신 예시
			// GitLab 서버와 통신할 GitLabApi 인스턴스 생성
			// Create a GitLabApi instance to communicate with your GitLab server

			GitlabAccess.gitLabApi = new GitLabApi("https://lab.ssafy.com", "qUpsyG1eYZvfb3tqfiis");
			Object projectIdOrPath = 118155L;
			String branchName = "master";

			// git repository branch 검증 요청
			if (GitlabAccess.isGitlabBranch(projectIdOrPath, branchName)) {
				System.out.println("Yes!!!!");
			}else {
				System.out.println("no!!!!!!!!!!");
			}
		*/


	}
}
