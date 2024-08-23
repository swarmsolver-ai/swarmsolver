package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ResourceDescriptor {
    private String name;
    private String description;
}
