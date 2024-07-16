package ai.swarmsolver.backend.config;

import ai.swarmsolver.backend.app.ApplicationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "ai.swarmsolver")
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

}
