package com.ssafy.dockerby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DockerbyApplication {
	public static void main(String[] args) {
		SpringApplication.run(DockerbyApplication.class, args);

	}
}
