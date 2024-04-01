package br.com.enutri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EnutriApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnutriApplication.class, args);
	}

	// @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// 	return new WebMvcConfigurer() {
	// 		@Override
	// 		public void addCorsMappings(CorsRegistry registry) {
	// 			registry.addMapping("/**")
	// 				.allowedOrigins("http://localhost:8081")
	// 				.allowedMethods("*")
	// 				.allowedHeaders("*");
	// 		}
	// 	};
	// }
}
