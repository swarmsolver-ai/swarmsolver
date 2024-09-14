package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
public class CommandToolSettings {

    private final String initialDir;

    private final String description;

    @Singular
    private final List<CommandTemplate> commandTemplates;


}
