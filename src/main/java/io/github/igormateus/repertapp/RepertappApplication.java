package io.github.igormateus.repertapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // (exclude = {SecurityAutoConfiguration.class})
public class RepertappApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepertappApplication.class, args);
	}
}
