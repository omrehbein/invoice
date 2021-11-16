package com.invoice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.invoice.controller"))
				.paths(PathSelectors.any()).build().apiInfo(getApiInfo())
				.protocols(new HashSet<>(Arrays.asList("http", "https")));
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("Unit Test Demonstration", "Unit Test Demonstration", "1.0.0",
				"TERMS OF SERVICE URL", null, "LICENSE", null, Collections.emptyList());
	}
}