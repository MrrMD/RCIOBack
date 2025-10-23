package ru.smallstash.rcoiback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get("F:/uploads").toAbsolutePath().normalize();

        registry.addResourceHandler("/media/**")
                .addResourceLocations(uploadDir.toUri().toString())
                .setCachePeriod(3600);
    }
}
