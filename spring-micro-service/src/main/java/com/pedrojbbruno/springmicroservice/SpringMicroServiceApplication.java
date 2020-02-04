package com.pedrojbbruno.springmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMicroServiceApplication.class, args);
	}

}
