package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CaseApplication {
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(CaseApplication.class, args);
	}

}
