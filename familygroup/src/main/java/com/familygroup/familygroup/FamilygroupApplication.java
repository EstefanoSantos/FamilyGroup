package com.familygroup.familygroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.familygroup.familygroup.models")
@SpringBootApplication
public class FamilygroupApplication {

	public static void main(String[] args) {
		SpringApplication.run(FamilygroupApplication.class, args);
	}

}
