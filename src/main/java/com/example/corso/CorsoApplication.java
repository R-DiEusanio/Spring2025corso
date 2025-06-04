package com.example.corso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CorsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorsoApplication.class, args);
	}

}
