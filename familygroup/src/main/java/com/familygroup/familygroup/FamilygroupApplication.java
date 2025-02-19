package com.familygroup.familygroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@EntityScan(basePackages = "com.familygroup.familygroup.models")
@OpenAPIDefinition(info = @Info(title = "Swagger OpenApi", version = "1", description = "Famiy Group Api"))
@SpringBootApplication
public class FamilygroupApplication {

	public static void main(String[] args) {
		SpringApplication.run(FamilygroupApplication.class, args);
	}

}
