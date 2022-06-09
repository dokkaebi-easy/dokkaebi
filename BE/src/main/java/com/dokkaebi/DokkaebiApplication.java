package com.dokkaebi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DokkaebiApplication {
	public static void main(String[] args) {
		SpringApplication.run(DokkaebiApplication.class, args);

	}
}
