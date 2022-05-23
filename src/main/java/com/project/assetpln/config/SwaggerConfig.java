package com.project.assetpln.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@SuppressWarnings("deprecation")
	@Bean
	public Docket api() {

		String title = "API Project for Asset PLN";
		String description = "Api documentation for project asset pln";
		String version = "1.0";
		ApiInfo apiInfo = new ApiInfo(title, description, version, null, null, null, null);

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select()
				.apis(RequestHandlerSelectors.basePackage("com.project.assetpln.controller")).paths(PathSelectors.any()).build();
	}
}
