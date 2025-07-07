package com.ia.alexander;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ia.alexander.client.ia")
public class AlexanderApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlexanderApplication.class, args);
	}

}
