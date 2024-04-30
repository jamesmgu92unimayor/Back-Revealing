package com.delitech.revealing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RevealingPopayanApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevealingPopayanApplication.class, args);
	}

}
