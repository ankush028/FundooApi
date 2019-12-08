package com.bridgelabz.fundoonote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@Configuration
public class SwaggerConfig {


	/**
	 * @return Object of Docket
	 */
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bridgelabz.fundoonote"))
				.build().apiInfo(metaInfo());
		
				
	}

	/**
	 * @return api Meta Information to show in swagger page
	 */
	private ApiInfo metaInfo() {
		
		return new ApiInfo(
				"Bridgelabz Fundoo Note Api",
				"ANKUSH AGRAWAL",
				"1.0",
				"Terms and service",
				new Contact("ANKUSH AGRAWAL","https://www.bridgelabz.com/","akag0284@gmail.com"),
				"Apache License version 2.0",
				"https://httpd.apache.org/docs/2.4/ssl/ssl_howto.html");
	}
}
	