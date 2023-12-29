package com.roovies.securityjwtexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.roovies")
public class SecurityJwtExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityJwtExampleApplication.class, args);
	}

}
