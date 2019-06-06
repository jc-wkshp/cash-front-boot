package com.innov.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CashFrontApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CashFrontApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
	   return new RestTemplate();
	}
}