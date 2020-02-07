package com.pedrojbbruno.microservice.personservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringPersonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPersonServiceApplication.class, args);
	}

}
