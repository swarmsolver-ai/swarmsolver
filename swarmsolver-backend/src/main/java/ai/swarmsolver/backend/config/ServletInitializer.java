package ai.swarmsolver.backend.config;

import ai.swarmsolver.backend.SwarmSolverApp;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SwarmSolverApp.class);
    }

}