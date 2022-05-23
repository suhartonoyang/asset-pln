package com.project.assetpln;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Configuration
public class AssetPlnApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetPlnApplication.class, args);
	}

}
