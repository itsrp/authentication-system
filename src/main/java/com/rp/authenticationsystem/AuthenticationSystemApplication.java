package com.rp.authenticationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.rp.authenticationsystem.repository"})
public class AuthenticationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationSystemApplication.class, args);
	}
}
