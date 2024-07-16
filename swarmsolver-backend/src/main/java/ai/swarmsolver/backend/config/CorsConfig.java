package ai.swarmsolver.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedOrigins("*")  // Allow connections from your Angular app
        ; //.allowedOrigins("http://localhost:19006");
        registry.addMapping("/ws/**")  // Adjust the mapping based on your WebSocket endpoint
                .allowedOrigins("*")  // Allow connections from your Angular app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}