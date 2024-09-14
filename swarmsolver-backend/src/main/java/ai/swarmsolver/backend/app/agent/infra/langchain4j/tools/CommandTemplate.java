package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import lombok.Builder;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@Builder
public class CommandTemplate {

    private String description;

    private String example;

    private Pattern pattern;

}