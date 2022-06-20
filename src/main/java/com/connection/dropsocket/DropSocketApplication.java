package com.connection.dropsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DropSocketApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(DropSocketApplication.class, args);
	}
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }
	
	@Bean
	public RestTemplate getBean() {
		return new RestTemplate();
	}
}
