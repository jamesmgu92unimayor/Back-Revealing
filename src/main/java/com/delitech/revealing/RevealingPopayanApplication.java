package com.delitech.revealing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.delitech.revealing.entity")
@ComponentScan("com.delitech.revealing.repository")
public class RevealingPopayanApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevealingPopayanApplication.class, args);
	}

}
