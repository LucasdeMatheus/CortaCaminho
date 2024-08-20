package com.myproject.CortaCaminho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CortaCaminhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CortaCaminhoApplication.class, args);
	}

}
