package com.card.alumni.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liumingyu
 * @date 2019-11-19 11:00 PM
 */
@Configuration
public class CoresConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(Boolean.TRUE)
                .allowedMethods("OPTIONS", "GET", "POST", "DELETE", "PUT", "PATCH")
                .maxAge(3600);
    }

}
