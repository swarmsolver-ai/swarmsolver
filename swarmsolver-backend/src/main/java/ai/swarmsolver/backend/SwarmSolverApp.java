package ai.swarmsolver.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SwarmSolverApp {

    @Value("${server.port}")
    long port;

    public static void main(String[] args) {

        SpringApplication.run(SwarmSolverApp.class, args);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        System.out.println(String.format("SwarmSolver running at http://localhost:%s",  port));
    }

}
