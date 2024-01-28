package com.poc.bankerapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/accounts/{id}/withdraw/{amount}")
                .allowedOrigins("http://localhost:3000","http://192.168.29.143:3000") // Allow requests from this origin
                .allowedMethods("PATCH") // Allow these HTTP methods
                .allowedHeaders("*"); // Allow all headers
        
        registry.addMapping("/api/accounts/{id}/deposit/{amount}")
		        .allowedOrigins("http://localhost:3000","http://192.168.29.143:3000") // Allow requests from this origin
		        .allowedMethods("PATCH") // Allow these HTTP methods
		        .allowedHeaders("*"); // Allow all headers
        
        registry.addMapping("/api/users/getuser")
		        .allowedOrigins("http://localhost:3000","http://192.168.29.143:3000") // Allow requests from this origin
		        .allowedMethods("POST") // Allow these HTTP methods
		        .allowedHeaders("*"); // Allow all headers
        
        registry.addMapping("/login")
		        .allowedOrigins("*") // Allow requests from this origin
		        .allowedMethods("POST") // Allow these HTTP methods
		        .allowedHeaders("*"); // Allow all headers
    }
    
}
