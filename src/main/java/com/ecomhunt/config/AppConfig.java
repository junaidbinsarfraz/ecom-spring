package com.ecomhunt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    @Value("${allowed.origins}")
    private String[] allowedOrigins;

    @Value("${spring.data.rest.base-path}")
    private String baseUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(this.baseUrl + "/**").allowedOrigins(this.allowedOrigins);
    }
}
